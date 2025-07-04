package com.irons.projectc.chapters

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.transition.Transition
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.irons.projectc.MainActivity
import com.irons.projectc.R
import com.irons.projectc.databinding.ActivityChapterBinding
import com.irons.projectc.levels.LevelsActivity
import kotlin.jvm.java

class ChapterActivity : AppCompatActivity() {

    lateinit var chapterBinding: ActivityChapterBinding

    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()

    private var userStatsListener: ValueEventListener? = null
    private lateinit var userStatsRef: DatabaseReference

    private val TOTAL_CHAPTERS = 2
    private var currentChapterNo: Int = 0

    private val chapterUnlockCodes = mapOf(
        0 to "GOOD", // Chapter 0 code
//        1 to "NEXT", // Chapter 1 code - EXAMPLE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        chapterBinding = ActivityChapterBinding.inflate(layoutInflater)
        setContentView(chapterBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentChapterNo = intent.getIntExtra("chapterNo", 0)

        updateChapterTitle()

        auth.currentUser?.uid?.let { userId ->
            userStatsRef = database.reference.child("users").child(userId).child("stats")
        }

        chapterBinding.btn1.setOnClickListener {
            startLevelActivity(1)
        }
        chapterBinding.btn2.setOnClickListener {
            startLevelActivity(2)
        }
        chapterBinding.btn3.setOnClickListener {
            startLevelActivity(3)
        }
        chapterBinding.btn4.setOnClickListener {
            startLevelActivity(4)
        }

        chapterBinding.btnNext.setOnClickListener {
            val currentStats = loadStatsFromLocal()
            val isChapterMarkedCompletedInFirebase = currentStats?.get("ch${currentChapterNo}stat") ?: false

            if(currentChapterNo == TOTAL_CHAPTERS - 1) {
                Toast.makeText(
                    this@ChapterActivity,
                    getString(R.string.congrats_message),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (isChapterMarkedCompletedInFirebase) {

                    val nextChapterNo = currentChapterNo + 1
                    val intent = Intent(this@ChapterActivity, ChapterActivity::class.java)
                    intent.putExtra("chapterNo", nextChapterNo)
                    startActivity(intent)
                    finish()
                } else {
                    val inputCode = chapterBinding.chapterCodeInput.text.toString().trim()
                    val expectedCode = chapterUnlockCodes[currentChapterNo]

                    if (expectedCode != null && inputCode == expectedCode) {
                        userStatsRef.child("ch${currentChapterNo}stat").setValue(true)
                        Toast.makeText(this, getString(R.string.code_accepted), Toast.LENGTH_SHORT)
                            .show()

                        if (currentChapterNo < TOTAL_CHAPTERS - 1) {
                            currentChapterNo++
                            val intent = Intent(this@ChapterActivity, ChapterActivity::class.java)
                            intent.putExtra("chapterNo", currentChapterNo)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                getString(R.string.congrats_message),
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@ChapterActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.code_rejected), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        chapterBinding.btnBack.setOnClickListener {
            currentChapterNo--;

            val intent = Intent(this@ChapterActivity, ChapterActivity::class.java)
            intent.putExtra("chapterNo", currentChapterNo)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        attachUserStatsListener()
    }

    private fun updateChapterTitle() {
        when (currentChapterNo) {
            0 -> {
                chapterBinding.tvGameTitle.setText(R.string.chapter_title_0)
                chapterBinding.btn1.setText(R.string.level_0_1)
                chapterBinding.btn2.setText(R.string.level_0_2)
                chapterBinding.btn3.setText(R.string.level_0_3)
                chapterBinding.btn4.setText(R.string.level_0_4)
            }
            1 -> {
                chapterBinding.tvGameTitle.setText(R.string.chapter_title_1)
                chapterBinding.btn1.setText(R.string.level_1_1)
                chapterBinding.btn2.setText(R.string.level_1_2)
                chapterBinding.btn3.setText(R.string.level_1_3)
                chapterBinding.btn4.setText(R.string.level_1_4)
            }
            2 -> {
                chapterBinding.tvGameTitle.setText(R.string.chapter_title_2)
                chapterBinding.btn1.setText(R.string.level_2_1)
                chapterBinding.btn2.setText(R.string.level_2_2)
                chapterBinding.btn3.setText(R.string.level_2_3)
                chapterBinding.btn4.setText(R.string.level_2_4)
            }
        }
    }

    private fun startLevelActivity(levelNo: Int) {
        val intent = Intent(this@ChapterActivity, LevelsActivity::class.java)
        intent.putExtra("chapterNo", currentChapterNo)
        intent.putExtra("levelNo", levelNo)
        startActivity(intent)
    }

    //************************** Saving and retrieving user stats **************************

    private fun attachUserStatsListener() {
        // Ensure user is logged in and userStatsRef is initialized
        if (!::userStatsRef.isInitialized) {
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
                        saveStatsLocally(stats)
                        updateUiWithStats(stats) // Create this function to update your UI
                    } else {
                        // Potentially create default stats if parsing fails AND it's the first load
                        // Be careful not to overwrite good local data if this is just a transient parse error on an update
                    }
                } else {
                    // It's important that createDefaultStatsAndSaveLocally ALSO updates the UI
                    // or calls updateUiWithStats with the default values after saving.
                    createDefaultStatsAndSaveLocally(userStatsRef.parent) // pass the parent node (userRef)
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(
                    this@ChapterActivity,
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
            updateUiWithStats(localStats)
        } else {
            // No local stats either, perhaps show some default state or an error
            // You might want to create and display default stats for the UI even if Firebase is down
            val defaultStats = mutableMapOf<String, Boolean>()

            defaultStats["ch${currentChapterNo}stat"] = false
            for (i in 1..4) { // 4 levels per chapter
                defaultStats["lvl${currentChapterNo}${i}stat"] = false
            }

            updateUiWithStats(defaultStats) // Update UI with defaults
            Toast.makeText(
                this,
                "Failed to load user data. Using default view.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun updateUiWithStats(stats: Map<String, Boolean>) {
        val chStatValue = stats["ch${currentChapterNo}stat"] ?: false
        val lvl1statValue = stats["lvl${currentChapterNo}1stat"] ?: false
        val lvl2statValue = stats["lvl${currentChapterNo}2stat"] ?: false
        val lvl3statValue = stats["lvl${currentChapterNo}3stat"] ?: false
        val lvl4statValue = stats["lvl${currentChapterNo}4stat"] ?: false

        chapterBinding.btnNext.isVisible = lvl4statValue
        chapterBinding.etInputCode.isEnabled = lvl4statValue
        chapterBinding.etInputCode.isVisible = currentChapterNo < TOTAL_CHAPTERS - 1
        chapterBinding.btnBack.isVisible = currentChapterNo > 0

        val animator = AnimatorInflater.loadAnimator(
            this@ChapterActivity,
            R.animator.button_move_left
        ) as ObjectAnimator
        animator.target = chapterBinding.btn1
        animator.start()

        if (lvl1statValue) {
            chapterBinding.btn2.alpha = 1f
            chapterBinding.btn2.isClickable = true
            val animator2 = AnimatorInflater.loadAnimator(
                this@ChapterActivity,
                R.animator.button_move_up
            ) as ObjectAnimator
            animator2.target = chapterBinding.btn2
            animator2.start()
        } else {
            chapterBinding.btn2.alpha = 0.5f
            chapterBinding.btn2.isClickable = false
        }

        if (lvl2statValue) {
            chapterBinding.btn3.alpha = 1f
            chapterBinding.btn3.isClickable = true
            val animator3 = AnimatorInflater.loadAnimator(
                this@ChapterActivity,
                R.animator.button_move_right
            ) as ObjectAnimator
            animator3.target = chapterBinding.btn3
            animator3.start()
        } else {
            chapterBinding.btn3.alpha = 0.5f
            chapterBinding.btn3.isClickable = false
        }

        if (lvl3statValue) {
            chapterBinding.btn4.alpha = 1f
            chapterBinding.btn4.isClickable = true
            val animator4 = AnimatorInflater.loadAnimator(
                this@ChapterActivity,
                R.animator.button_move_down
            ) as ObjectAnimator
            animator4.target = chapterBinding.btn4
            animator4.start()
        } else {
            chapterBinding.btn4.alpha = 0.5f
            chapterBinding.btn4.isClickable = false
        }
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
        val defaultStats = mutableMapOf<String, Boolean>()

        for (chapterNum in 0 until TOTAL_CHAPTERS) {
            defaultStats["ch${chapterNum}stat"] = false
            for (levelNum in 1..4) { // Assuming 4 levels per chapter
                defaultStats["lvl${chapterNum}${levelNum}stat"] = false
            }
        }
        // Pass the actual "stats" node reference if you are setting value directly on it
        // Or, if userNode is the parent (e.g., database.reference.child("users").child(userId))
        // then use userNode.child("stats").setValue(defaultStats)
        val actualStatsRef =
            userNode?.child("stats") ?: userStatsRef // Fallback if userNode is null

        actualStatsRef.setValue(defaultStats)
            .addOnSuccessListener {
                saveStatsLocally(defaultStats)
                updateUiWithStats(defaultStats) // Important: Update UI with these defaults
            }
            .addOnFailureListener { e ->
                saveStatsLocally(defaultStats)
                updateUiWithStats(defaultStats) // Still update UI with defaults locally
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
    }

    private fun loadStatsFromLocal(): Map<String, Boolean>? {
        val sharedPreferences = getSharedPreferences("UserStatsPrefs", Context.MODE_PRIVATE)
        // Define the keys you expect to find
        val allPrefs = sharedPreferences.all
        val loadedStats = mutableMapOf<String, Boolean>()
        var hasAnyStat = false

        allPrefs.forEach { (key, value) ->
            if (value is Boolean) {
                loadedStats[key] = value
                hasAnyStat = true
            }
        }

        return if (hasAnyStat) {
            loadedStats
        } else {
            null // Or return an empty map if that makes more sense for your logic
        }
    }

    //**************************************************************************************
}