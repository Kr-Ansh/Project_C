package com.irons.projectc.chapters

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.values
import com.irons.projectc.R
import com.irons.projectc.databinding.ActivityChapter0Binding
import com.irons.projectc.levels.Levels0Activity

class Chapter0Activity : AppCompatActivity() {

    lateinit var chapter0Binding: ActivityChapter0Binding

    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    private var userStatsListener: ValueEventListener? = null
    private lateinit var userStatsRef: DatabaseReference // Reference to the "stats" node


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        chapter0Binding = ActivityChapter0Binding.inflate(layoutInflater)
        setContentView(chapter0Binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth.currentUser?.uid?.let { userId ->
            userStatsRef = database.reference.child("users").child(userId).child("stats")
        }

        chapter0Binding.btn01.setOnClickListener {
            val intent = Intent(this@Chapter0Activity, Levels0Activity::class.java)
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()

        attachUserStatsListener()
    }

    //************************** Saving and retrieving user stats **************************

    private fun attachUserStatsListener() {
        // Ensure user is logged in and userStatsRef is initialized
        if (!::userStatsRef.isInitialized) {
            Log.w(
                "Chapter0Activity",
                "User not logged in or userStatsRef not initialized. Cannot attach listener."
            )
            // Optionally, load from local or show login prompt
            loadStatsFromLocalAndUpdateUI() // Load local as fallback
            return
        }

        // If a listener already exists, remove it first to avoid multiple listeners
        userStatsListener?.let {
            userStatsRef.removeEventListener(it)
        }

        userStatsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val stats = snapshot.value as? Map<String, Boolean>
                    if (stats != null) {
                        Log.d("Chapter0Activity", "User stats updated from Firebase: $stats")
                        saveStatsLocally(stats)
                        updateUiWithStats(stats) // Create this function to update your UI
                    } else {
                        Log.e(
                            "Chapter0Activity",
                            "Failed to parse stats from Firebase snapshot."
                        )
                        // Potentially create default stats if parsing fails AND it's the first load
                        // Be careful not to overwrite good local data if this is just a transient parse error on an update
                    }
                } else {
                    Log.d(
                        "Chapter0Activity",
                        "No stats found in Firebase for this user. Creating defaults."
                    )
                    // It's important that createDefaultStatsAndSaveLocally ALSO updates the UI
                    // or calls updateUiWithStats with the default values after saving.
                    createDefaultStatsAndSaveLocally(userStatsRef.parent) // pass the parent node (userRef)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(
                    "Chapter0Activity",
                    "Error listening to user stats: ",
                    error.toException()
                )
                Toast.makeText(
                    this@Chapter0Activity,
                    "Error loading live data. Displaying local data.",
                    Toast.LENGTH_SHORT
                ).show()
                // Load local stats as a fallback and update UI
                loadStatsFromLocalAndUpdateUI()
            }
        }
        userStatsRef.addValueEventListener(userStatsListener!!)
    }

    private fun loadStatsFromLocalAndUpdateUI() {
        val localStats = loadStatsFromLocal()
        if (localStats != null) {
            Log.d("Chapter0Activity", "Using stats from local SharedPreferences.")
            updateUiWithStats(localStats)
        } else {
            // No local stats either, perhaps show some default state or an error
            Log.d("Chapter0Activity", "No local stats found.")
            // You might want to create and display default stats for the UI even if Firebase is down
            val defaultStats = mapOf(
                "ch0stat" to false, "lvl01stat" to false, "lvl02stat" to false,
                "lvl03stat" to false, "lvl04stat" to false
            )
            updateUiWithStats(defaultStats) // Update UI with defaults
            Toast.makeText(
                this,
                "Failed to load user data. Using default view.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun updateUiWithStats(stats: Map<String, Boolean>) {
        val ch0statValue = stats["ch0stat"] ?: false
        val lvl01statValue = stats["lvl01stat"] ?: false
        val lvl02statValue = stats["lvl02stat"] ?: false
        val lvl03statValue = stats["lvl03stat"] ?: false
        val lvl04statValue = stats["lvl04stat"] ?: false

        chapter0Binding.btnNext.isVisible = ch0statValue

        val animator = AnimatorInflater.loadAnimator(
            this@Chapter0Activity,
            R.animator.button_move_left
        ) as ObjectAnimator
        animator.target = chapter0Binding.btn01
        animator.start()

        if (lvl01statValue) {
            chapter0Binding.btn02.alpha = 1f
            chapter0Binding.btn02.isClickable = true
            val animator2 = AnimatorInflater.loadAnimator(
                this@Chapter0Activity,
                R.animator.button_move_up
            ) as ObjectAnimator
            animator2.target = chapter0Binding.btn02
            animator2.start()
        } else {
            chapter0Binding.btn02.alpha = 0.5f
            chapter0Binding.btn02.isClickable = false
        }

        if (lvl02statValue) {
            chapter0Binding.btn03.alpha = 1f
            chapter0Binding.btn03.isClickable = true
            val animator3 = AnimatorInflater.loadAnimator(
                this@Chapter0Activity,
                R.animator.button_move_right
            ) as ObjectAnimator
            animator3.target = chapter0Binding.btn03
            animator3.start()
        } else {
            chapter0Binding.btn03.alpha = 0.5f
            chapter0Binding.btn03.isClickable = false
        }

        if (lvl03statValue) {
            chapter0Binding.btn04.alpha = 1f
            chapter0Binding.btn04.isClickable = true
            val animator4 = AnimatorInflater.loadAnimator(
                this@Chapter0Activity,
                R.animator.button_move_down
            ) as ObjectAnimator
            animator4.target = chapter0Binding.btn04
            animator4.start()
        } else {
            chapter0Binding.btn04.alpha = 0.5f
            chapter0Binding.btn04.isClickable = false
        }

        chapter0Binding.etInputCode.isEnabled = lvl04statValue

        Log.d("Chapter0Activity", "UI updated with stats: $stats")
    }

    override fun onStop() {
        super.onStop()
        // Remove the listener when the activity is no longer visible to save resources
        // and prevent memory leaks.
        userStatsListener?.let {
            // Check if userStatsRef was initialized before trying to remove listener
            if (::userStatsRef.isInitialized) {
                userStatsRef.removeEventListener(it)
            }
            userStatsListener = null // Clear the reference
        }
    }

    private fun createDefaultStatsAndSaveLocally(userNode: DatabaseReference?) {
        val defaultStats = mapOf(
            "ch0stat" to false,
            "lvl01stat" to false,
            "lvl02stat" to false,
            "lvl03stat" to false,
            "lvl04stat" to false
        )
        // Pass the actual "stats" node reference if you are setting value directly on it
        // Or, if userNode is the parent (e.g., database.reference.child("users").child(userId))
        // then use userNode.child("stats").setValue(defaultStats)
        val actualStatsRef =
            userNode?.child("stats") ?: userStatsRef // Fallback if userNode is null

        actualStatsRef.setValue(defaultStats)
            .addOnSuccessListener {
                Log.d("Chapter0Activity", "Default stats created in Firebase successfully.")
                saveStatsLocally(defaultStats)
                updateUiWithStats(defaultStats) // Important: Update UI with these defaults
            }
            .addOnFailureListener { e ->
                Log.e("Chapter0Activity", "Error creating default stats in Firebase: ", e)
                saveStatsLocally(defaultStats)
                updateUiWithStats(defaultStats) // Still update UI with defaults locally
                Log.w("Chapter0Activity", "Saved default stats locally despite Firebase error.")
                Toast.makeText(
                    this,
                    "Failed to initialize user data online, using defaults.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun saveStatsLocally(stats: Map<String, Boolean>) {
        val sharedPreferences = getSharedPreferences("UserStatsPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            stats.forEach { (key, value) ->
                putBoolean(key, value)
            }
            apply() // Asynchronously saves the changes
        }
        Log.d("Chapter0Activity", "Stats saved locally to SharedPreferences.")
    }

    private fun loadStatsFromLocal(): Map<String, Boolean>? {
        val sharedPreferences = getSharedPreferences("UserStatsPrefs", Context.MODE_PRIVATE)
        // Define the keys you expect to find
        val statKeys = listOf("ch0stat", "lvl01stat", "lvl02stat", "lvl03stat", "lvl04stat")
        val loadedStats = mutableMapOf<String, Boolean>()

        // Check if at least one key exists to consider it "loaded"
        // This helps differentiate between no stats saved vs. all stats being false
        var hasAnyStat = false
        for (key in statKeys) {
            if (sharedPreferences.contains(key)) {
                hasAnyStat = true
                loadedStats[key] = sharedPreferences.getBoolean(
                    key,
                    false
                ) // Default to false if key somehow missing after check
            }
        }

        return if (hasAnyStat) {
            Log.d("Chapter0Activity", "Stats loaded from SharedPreferences: $loadedStats")
            loadedStats
        } else {
            Log.d("Chapter0Activity", "No stats found in SharedPreferences.")
            null // Or return an empty map if that makes more sense for your logic
        }
    }

    //**************************************************************************************
}