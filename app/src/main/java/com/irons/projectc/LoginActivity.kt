package com.irons.projectc

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.irons.projectc.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    lateinit var loginBinding: ActivityLoginBinding
    //*********************** Google Sign In ***********************
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    //***************************************************************

    //Firebase Database *****************************
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var usersRef: DatabaseReference = database.getReference("users")
    //***********************************************

    //******* User data ************
    private var pendingUserName: String = ""
    private var pendingUserEmail: String = ""
    private var pendingUserDob: String = ""
    //******************************

    //****** For DOB ***************
    var userDay = 0
    var userMonth = 0
    var userYear = 0
    //****************************

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //****************DOB picker************************
        loginBinding.userInputDOB.isFocusable = false
        loginBinding.userInputDOB.setOnClickListener {

            val cal: Calendar = Calendar.getInstance()
            userDay = cal.get(Calendar.DAY_OF_MONTH)
            userMonth = cal.get(Calendar.MONTH)
            userYear = cal.get(Calendar.YEAR)

            DatePickerDialog(this@LoginActivity, this@LoginActivity, userYear, userMonth, userDay).show()
        }
        //**************************************************

        //****************Google Sign In**********************
        registerActivityForGoogleSignIn()
        loginBinding.buttonGoogleSignIn.setOnClickListener {

            pendingUserName = loginBinding.userInputName.text.toString()
            pendingUserEmail = loginBinding.userInputEmail.text.toString()
            pendingUserDob = loginBinding.userInputDOB.text.toString()

            if(pendingUserName.isEmpty() || pendingUserEmail.isEmpty() || pendingUserDob.isEmpty()) {
                Toast.makeText(this@LoginActivity, R.string.empty_fields, Toast.LENGTH_SHORT).show()
            } else {

                signInGoogle()
            }
        }
        //*****************************************************

        //****************Facebook Sign In**********************
        loginBinding.buttonFacebookSignIn.setOnClickListener {
            Toast.makeText(this@LoginActivity, R.string.coming_soon, Toast.LENGTH_SHORT).show()
        }
        //******************************************************

        //****************Anonymous Sign In**********************
        loginBinding.buttonAnonymousSignIn.setOnClickListener {
            Toast.makeText(this@LoginActivity, R.string.coming_soon, Toast.LENGTH_SHORT).show()
        }
        //******************************************************
    }

    //*********************** Google Sign In ***********************
    private fun signInGoogle() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("150755625693-3dg4217ob7squa5ttss28lis8onjguf1.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent: Intent = googleSignInClient.signInIntent
        activityResultLauncher.launch(signInIntent)
    }

    private fun registerActivityForGoogleSignIn() {

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->

                val resultCode = result.resultCode
                val data = result.data

                if(resultCode == RESULT_OK && data != null) {

                    val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                    firebaseSignInWithGoogle(task)
                }
            }
        )
    }

    private fun firebaseSignInWithGoogle(task: Task<GoogleSignInAccount>) {

        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            Toast.makeText(applicationContext, "Welcome to Clue.dicode", Toast.LENGTH_SHORT).show()

            firebaseGoogleAccount(account)
        } catch (e: ApiException) {
            Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    // We can get user info from this method
    private fun firebaseGoogleAccount(account: GoogleSignInAccount) {

        val authCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(authCredential).addOnCompleteListener { task ->
            if(task.isSuccessful) {

                val firebaseUser = auth.currentUser
                if(firebaseUser != null) {
                    saveDataToFirebase(firebaseUser, pendingUserName, pendingUserEmail, pendingUserDob)

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(baseContext, R.string.error_try_again, Toast.LENGTH_SHORT).show()
                }
            } else {

                Toast.makeText(baseContext, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // User will remain logged in after logging in once
    override fun onStart() {
        super.onStart()

        val user = auth.currentUser

        if(user != null) {

            Toast.makeText(applicationContext, R.string.welcome_text, Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    //*********************************************************

    // Date picker
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        userDay = dayOfMonth
        userMonth = month + 1
        userYear = year

        loginBinding.userInputDOB.setText("$userDay/$userMonth/$userYear")
    }

    // Saving Data to Database
    private fun saveDataToFirebase(firebaseUser: FirebaseUser ,userName: String, userEmail: String, userDob: String) {

        val userId = firebaseUser.uid

        val userData = hashMapOf(
            "userName" to userName,
            "userEmail" to userEmail,
            "userDob" to userDob
        )

        usersRef.child(userId).setValue(userData).addOnSuccessListener {

            pendingUserName = ""
            pendingUserEmail = ""
            pendingUserDob = ""
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed to save user data: ${e.message}", Toast.LENGTH_LONG).show()
            // Handle the error, maybe retry or log
        }
    }
}