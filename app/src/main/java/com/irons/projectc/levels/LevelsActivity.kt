package com.irons.projectc.levels

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.irons.projectc.R
import com.irons.projectc.databinding.ActivityLevelsBinding
import java.util.Calendar

class LevelsActivity : AppCompatActivity() {

    lateinit var levelsBinding: ActivityLevelsBinding

    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("stats")

    private var currentChapterNo: Int = 0
    private var currentLevelNo: Int = 0

    data class LevelData(
        val chapter: Int,
        val level: Int,
        val titleResId: Int,
        val descriptionResId: Int,
        val questionResId: Int,
        val correctAnswer: String,
        val successMessageChar: String
    )

    private val allLevelsData = listOf(
        // Chapter 0
        LevelData(0, 1, R.string.level_title_0_1, R.string.level_description_0_1, R.string.level_question_0_1, "Clue.dicode", "G"),
        LevelData(0, 2, R.string.level_title_0_2, R.string.level_description_0_2, R.string.level_question_0_2, "interesting-morse", "O"),
        LevelData(0, 3, R.string.level_title_0_3, R.string.level_description_0_3, R.string.level_question_0_3, "mahabharata", "O"),
        LevelData(0, 4, R.string.level_title_0_4, R.string.level_description_0_4, R.string.level_question_0_4, "KrAnsh", "D"),

        // Chapter 1
        LevelData(1, 1, R.string.level_title_1_1, R.string.level_description_1_1, R.string.level_question_1_1, "ShiftedCaesarCipher", "N"),
        LevelData(1, 2, R.string.level_title_1_2, R.string.level_description_1_2, R.string.level_question_1_2, "ReversedAtbashCipher", "E"),
        LevelData(1, 3, R.string.level_title_1_3, R.string.level_description_1_3, R.string.level_question_1_3, "LockAndKey", "X"),
        LevelData(1, 4, R.string.level_title_1_4, R.string.level_description_1_4, R.string.level_question_1_4, "FunnyBits", "T"),

        // Chapter 2
        LevelData(2, 1, R.string.level_title_2_1, R.string.level_description_2_1, R.string.level_question_2_1, "London", "N"),
        LevelData(2, 2, R.string.level_title_2_2, R.string.level_description_2_2, R.string.level_question_2_2, "Taj Mahal", "O"),
        LevelData(2, 3, R.string.level_title_2_3, R.string.level_description_2_3, R.string.level_question_2_3, "JR Suarez", "O"),
        LevelData(2, 4, R.string.level_title_2_4, R.string.level_description_2_4, R.string.level_question_2_4, "Jimmie", "B"),

        // Chapter 3
        LevelData(3, 1, R.string.level_title_3_1, R.string.level_description_3_1, R.string.level_question_3_1, "Fantastic", "H"),
        LevelData(3, 2, R.string.level_title_3_2, R.string.level_description_3_2, R.string.level_question_3_2, ".-. .- -- .- -.-- .- -. .-", "E"),
        LevelData(3, 3, R.string.level_title_3_3, R.string.level_description_3_3, R.string.level_question_3_3, "Paradox", "H"),
        LevelData(3, 4, R.string.level_title_3_4, R.string.level_description_3_4, R.string.level_question_3_4, "user", "E"),

        // Chapter 4
        LevelData(4, 1, R.string.level_title_4_1, R.string.level_description_4_1, R.string.level_question_4_1, "Phantom", "E"),
        LevelData(4, 2, R.string.level_title_4_2, R.string.level_description_4_2, R.string.level_question_4_2, "Alpha101", "V"),
        LevelData(4, 3, R.string.level_title_4_3, R.string.level_description_4_3, R.string.level_question_4_3, "Delhi", "I"),
        LevelData(4, 4, R.string.level_title_4_4, R.string.level_description_4_4, R.string.level_question_4_4, "The Oberoi Amarvilas", "L"),

        // Add data for Chapters
    )

    private var currentLevelData: LevelData? = null

    private var inputText = StringBuilder()
    private var lastVolumeUpTime: Long = 0
    private var lastVolumeDownTime: Long = 0
    private var volumeUpTapCount = 0
    private var volumeDownTapCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        levelsBinding = ActivityLevelsBinding.inflate(layoutInflater)
        setContentView(levelsBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentChapterNo = intent.getIntExtra("chapterNo", -1)
        currentLevelNo = intent.getIntExtra("levelNo", -1)

        loadLevelContent()

        customLogicAndDesignForLevel(currentChapterNo, currentLevelNo)

        levelsBinding.buttonSubmit.setOnClickListener {
            handleSubmit()
        }
        levelsBinding.btnHint!!.setOnClickListener {
            Toast.makeText(this, "Hints\nComing soon...", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this@LevelsActivity, HintActivity::class.java)
//            startActivity(intent)
        }
        levelsBinding.btnNotes!!.setOnClickListener {
            val intent = Intent(this@LevelsActivity, NotesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadLevelContent() {
        currentLevelData = allLevelsData.find { it.chapter == currentChapterNo && it.level == currentLevelNo }

        currentLevelData?.let { data ->
            levelsBinding.tvLevelTitle.setText(data.titleResId)
            levelsBinding.tvAboutLevel.text = getPersonalizedString(data.descriptionResId)
            levelsBinding.tvQuestion.setText(data.questionResId)
            levelsBinding.answerUserInput.setText("")
        }
    }

    private fun handleSubmit() {
        val userAnswer = levelsBinding.answerUserInput.text.toString().trim()
        var correctAnswer = currentLevelData?.correctAnswer

        if(currentChapterNo == 3 && currentLevelNo == 4) {
            val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
            correctAnswer = prefs.getString("playerName", "user")
        }

        if(userAnswer.equals(correctAnswer, ignoreCase = true)) {

            val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean("isLevel3_3Entered", false).apply()
            editor.putBoolean("isExitClicked", false).apply()

            val statKey = "lvl${currentChapterNo}${currentLevelNo}stat"
            ref.child(statKey).setValue(true).addOnSuccessListener {
                levelsBinding.tvAboutLevel.text = currentLevelData!!.successMessageChar
                levelsBinding.tvQuestion.setText(R.string.go_back_text)
                Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
        }
    }

    fun Context.getPersonalizedString(@StringRes stringId: Int): String {
        val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        val playerName = prefs.getString("playerName", "User")
        val rawString = this.getString(stringId)
        return rawString.replace("user", playerName ?: "User")
    }

    // Custom logic for level 3.2
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (currentChapterNo == 3 && currentLevelNo == 2) {
            handleMorseInput(keyCode)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun handleMorseInput(keyCode: Int) {
        val currentTime = System.currentTimeMillis()
        val editText = levelsBinding.answerUserInput

        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (currentTime - lastVolumeUpTime < 300) {
                    volumeUpTapCount++
                } else {
                    volumeUpTapCount = 1
                }
                lastVolumeUpTime = currentTime

                if (volumeUpTapCount == 2) {
                    inputText.deleteCharAt(inputText.length - 1)
                    inputText.append(" ")
                    volumeUpTapCount = 0
                    Toast.makeText(this, "Space added", Toast.LENGTH_SHORT).show()
                } else {
                    inputText.append("-")
                }
            }

            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (currentTime - lastVolumeDownTime < 300) {
                    volumeDownTapCount++
                } else {
                    volumeDownTapCount = 1
                }
                lastVolumeDownTime = currentTime

                if (volumeDownTapCount == 2) {
                    if (inputText.isNotEmpty()) {
                        inputText.deleteCharAt(inputText.length - 1)
                        inputText.deleteCharAt(inputText.length - 1)
                    }
                    volumeDownTapCount = 0
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                } else {
                    inputText.append(".")
                }
            }

            KeyEvent.KEYCODE_BACK -> {
                finish()
            }
        }

        editText.setText(inputText.toString())
    }

    // Checks for dob
    fun isTodayBirthday(dob: String): Boolean {
        return try {
            val parts = dob.split("/")
            if (parts.size < 2) return false

            val dobDay = parts[0].toInt()
            val dobMonth = parts[1].toInt()

            val today = Calendar.getInstance()
            val todayDay = today.get(Calendar.DAY_OF_MONTH)
            val todayMonth = today.get(Calendar.MONTH) + 1

            dobDay == todayDay && dobMonth == todayMonth
        } catch (e: Exception) {
            false
        }
    }

    // Custom logic or designs for specific levels
    private fun customLogicAndDesignForLevel(chapterNo: Int, levelNo: Int) {

        // Custom logic and design for level 3
        if(currentChapterNo == 3) {
            // Custom design for level 3.2
            if (currentLevelNo == 2) levelsBinding.answerUserInput.isFocusable =
                false
            else levelsBinding.answerUserInput.isFocusable = true

            // Custom logic for level 3.3
            if (currentLevelNo == 3) {
                val prefs: SharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putBoolean("isLevel3_3Entered", true).apply()

                levelsBinding.tvQuestion.isVisible = prefs.getBoolean("isExitClicked", false)
            }

            // Custom logic for level 3.4
            if (currentLevelNo == 4) {
                val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
                val playerDob = prefs.getString("playerDOB", "0").toString()
                if (!isTodayBirthday(playerDob)) levelsBinding.tvQuestion.text = "\uD83C\uDF81"
            }
        }
    }
}