package com.irons.projectc

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.irons.projectc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Play button
        mainBinding.btnPlay.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)
        }

        // Settings button
        mainBinding.btnSettings.setOnClickListener {
            Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
        }

        // About me button
        mainBinding.btnAbout.setOnClickListener {
            Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
        }

        // Sign out button
        mainBinding.btnSignOut.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            // Only for Google login
            val gso  = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            googleSignInClient.signOut().addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this, "Sign Out Successful", Toast.LENGTH_LONG).show()
                }
            }

            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Exit button
        mainBinding.btnExit.setOnClickListener {
            finishAffinity()
        }
    }

    // All animations are called here
    override fun onStart() {
        super.onStart()

        val animatorTitle = AnimatorInflater.loadAnimator(this@MainActivity, R.animator.main_title_alpha) as ObjectAnimator
        animatorTitle.target = mainBinding.tvMainTitle
        animatorTitle.start()

        val animator = AnimatorInflater.loadAnimator(this@MainActivity, R.animator.button_move_right) as ObjectAnimator
        animator.target = mainBinding.btnPlay
        animator.start()

        val animator2 = AnimatorInflater.loadAnimator(this@MainActivity, R.animator.button_move_left) as ObjectAnimator
        animator2.target = mainBinding.btnSettings
        animator2.start()

        val animator3 = AnimatorInflater.loadAnimator(this@MainActivity, R.animator.button_move_right) as ObjectAnimator
        animator3.target = mainBinding.btnAbout
        animator3.start()

        val animator4 = AnimatorInflater.loadAnimator(this@MainActivity, R.animator.button_move_left) as ObjectAnimator
        animator4.target = mainBinding.btnSignOut
        animator4.start()

        val animator5 = AnimatorInflater.loadAnimator(this@MainActivity, R.animator.button_move_right) as ObjectAnimator
        animator5.target = mainBinding.btnExit
        animator5.start()
    }
}