package com.irons.projectc.mainScreen

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.irons.projectc.LoginActivity
import com.irons.projectc.R
import com.irons.projectc.chapters.ChapterActivity
import com.irons.projectc.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    private var mediaPlayer: MediaPlayer ?= null

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

        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.btn_sound)


        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}
        }

        val prefs = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        // Level 3
        editor.putBoolean("isLevel3_3Entered", false).apply()
        editor.putBoolean("isExitClicked", false).apply()
        // Final level
        editor.putBoolean("final_level_started", false).apply()
        editor.putBoolean("phase1_completed", false).apply()
        editor.putBoolean("phase2_completed", false).apply()
        editor.putBoolean("phase3_completed", false).apply()

        // Play button
        mainBinding.btnPlay.setOnClickListener {
            // Play sound
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                    prepare() // Prepare media player to play again from the start
                }
                start()
            }
            if(!isNetworkAvailable(this)) {
                Toast.makeText(this@MainActivity, "No internet connection detected.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this@MainActivity, ChapterActivity::class.java)
            intent.putExtra("chapterNo", 0)
            startActivity(intent)
        }

        // Settings button
        mainBinding.btnSettings.setOnClickListener {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                    prepare()
                }
                start()
            }
            if(!isNetworkAvailable(this)) {
                Toast.makeText(this@MainActivity, "No internet connection detected.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }

        // About me button
        mainBinding.btnAbout.setOnClickListener {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                    prepare()
                }
                start()
            }
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
        }

        // Sign out button
        mainBinding.btnSignOut.setOnClickListener {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                    prepare()
                }
                start()
            }

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
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                    prepare()
                }
                start()
            }
            if(prefs.getBoolean("isLevel3_3Entered", false)) {
                Toast.makeText(this, "Paradox", Toast.LENGTH_SHORT).show()
                editor.putBoolean("isExitClicked", true).apply()
            } else {
                finishAffinity()
            }
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


        // UI update for final level
        val prefs = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
        if(prefs.getBoolean("final_level_started", false) && !prefs.getBoolean("phase1_completed", false)) {
            mainBinding.tvMainTitle.text = "XYZABC"
        } else {
            mainBinding.tvMainTitle.setText(R.string.main_title)
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}