package com.irons.projectc.mainScreen

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.irons.projectc.R
import com.irons.projectc.databinding.ActivitySettingsBinding
import androidx.core.net.toUri

class SettingsActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    lateinit var binding: ActivitySettingsBinding

    private var mediaPlayer: MediaPlayer ?= null

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var usersRef: DatabaseReference = database.getReference("users").child(FirebaseAuth.getInstance().currentUser!!.uid)

    var userDay = 0
    var userMonth = 0
    var userYear = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.btn_sound)

        loadUserData()

        binding.tvPrivacy.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                "https://kr-ansh.github.io/Project_C/app/src/main/assets/Privacy_Policy".toUri()
            startActivity(intent)
        }
        binding.tvTerms.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = "https://kr-ansh.github.io/Project_C/terms".toUri()
            startActivity(intent)
        }

        binding.btnFeedback.setOnClickListener {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                    prepare()
                }
                start()
            }
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = "mailto:".toUri()
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("kranshdev20@gmail.com"))
            startActivity(intent)
        }

        binding.btnSave.setOnClickListener {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                    prepare()
                }
                start()
            }
            val newUsername = binding.etUsername.text.toString()
            val newDob = binding.etDob.text.toString()
            val newEmail = binding.etEmail.text.toString()

            saveUserData(newUsername, newDob, newEmail)
            finish()
        }

        binding.etDob.isFocusable = false
        binding.etDob.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            userDay = cal.get(Calendar.DAY_OF_MONTH)
            userMonth = cal.get(Calendar.MONTH)
            userYear = cal.get(Calendar.YEAR)

            DatePickerDialog(this@SettingsActivity, this@SettingsActivity, userYear, userMonth, userDay).show()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun loadUserData() {

        val prefs = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
        val userName = prefs.getString("playerName", "User")
        val userEmail = prefs.getString("playerEmail", "Email")
        val userDob = prefs.getString("playerDOB", "DOB")

        binding.etUsername.setText(userName)
        binding.etEmail.setText(userEmail)
        binding.etDob.setText(userDob)
    }

    private fun saveUserData(newUsername: String, newDob: String, newEmail: String) {

        usersRef.child("userName").setValue(newUsername)
        usersRef.child("userEmail").setValue(newEmail)
        usersRef.child("userDob").setValue(newDob)

        // Saving data locally
        val prefs = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("playerName", newUsername).apply()
        editor.putString("playerEmail", newEmail).apply()
        editor.putString("playerDOB", newDob).apply()
        editor.apply()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        userDay = dayOfMonth
        userMonth = month + 1
        userYear = year

        binding.etDob.setText("$userDay/$userMonth/$userYear")
    }
}