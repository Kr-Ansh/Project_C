package com.irons.projectc.levels

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.irons.projectc.R
import com.irons.projectc.databinding.ActivityLevelsBinding

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
        LevelData(2, 1, R.string.level_title_2_1, R.string.level_description_2_1, R.string.level_question_2_1, "answer21", "N"),
        LevelData(2, 2, R.string.level_title_2_2, R.string.level_description_2_2, R.string.level_question_2_2, "answer22", "E"),
        LevelData(2, 3, R.string.level_title_2_3, R.string.level_description_2_3, R.string.level_question_2_3, "answer23", "X"),
        LevelData(2, 4, R.string.level_title_2_4, R.string.level_description_2_4, R.string.level_question_2_4, "answer24", "T"),

        // Add data for Chapter 2, Chapter 3, etc.
    )

    private var currentLevelData: LevelData? = null

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

        levelsBinding.buttonSubmit.setOnClickListener {
            handleSubmit()
        }

/*
//        if(lvlNo == "01") {
//            levelsBinding.tvLevelTitle.setText(R.string.level_title_0_1)
//            levelsBinding.tvAboutLevel.setText(R.string.level_description_0_1)
//            levelsBinding.tvQuestion.setText(R.string.level_question_0_1)
//
//            levelsBinding.buttonSubmit.setOnClickListener {
//
//                if(levelsBinding.answerUserInput.text.toString().trim() == answer01){
//                    ref.child("lvl01stat").setValue(true)
//                    levelsBinding.tvAboutLevel.text = "G"
//                    levelsBinding.tvQuestion.setText(R.string.go_back_text)
//                    Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
//                }
//            }
//        } else if(lvlNo == "02") {
//            levelsBinding.tvLevelTitle.setText(R.string.level_title_0_2)
//            levelsBinding.tvAboutLevel.setText(R.string.level_description_0_2)
//            levelsBinding.tvQuestion.setText(R.string.level_question_0_2)
//
//            levelsBinding.buttonSubmit.setOnClickListener {
//
//                if(levelsBinding.answerUserInput.text.toString().lowercase().trim() == answer02){
//                    ref.child("lvl02stat").setValue(true)
//                    levelsBinding.tvAboutLevel.text = "O"
//                    levelsBinding.tvQuestion.setText(R.string.go_back_text)
//                    Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
//                }
//            }
//        } else if(lvlNo == "03") {
//            levelsBinding.tvLevelTitle.setText(R.string.level_title_0_3)
//            levelsBinding.tvAboutLevel.setText(R.string.level_description_0_3)
//            levelsBinding.tvQuestion.setText(R.string.level_question_0_3)
//
//            levelsBinding.buttonSubmit.setOnClickListener {
//
//                if(levelsBinding.answerUserInput.text.toString().lowercase().trim() == answer03){
//                    ref.child("lvl03stat").setValue(true)
//                    levelsBinding.tvAboutLevel.text = "O"
//                    levelsBinding.tvQuestion.setText(R.string.go_back_text)
//                    Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
//                }
//            }
//        } else if(lvlNo == "04") {
//            levelsBinding.tvLevelTitle.setText(R.string.level_title_0_4)
//            levelsBinding.tvAboutLevel.setText(R.string.level_description_0_4)
//            levelsBinding.tvQuestion.setText(R.string.level_question_0_4)
//
//            levelsBinding.buttonSubmit.setOnClickListener {
//
//                if(levelsBinding.answerUserInput.text.toString().trim() == answer04){
//                    ref.child("lvl04stat").setValue(true)
//                    levelsBinding.tvAboutLevel.text = "D"
//                    levelsBinding.tvQuestion.setText(R.string.go_back_text)
//                    Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
 */
    }

    private fun loadLevelContent() {
        currentLevelData = allLevelsData.find { it.chapter == currentChapterNo && it.level == currentLevelNo }

        currentLevelData?.let { data ->
            levelsBinding.tvLevelTitle.setText(data.titleResId)
            levelsBinding.tvAboutLevel.setText(data.descriptionResId)
            levelsBinding.tvQuestion.setText(data.questionResId)
            levelsBinding.answerUserInput.setText("")
        }
    }

    private fun handleSubmit() {
        val userAnswer = levelsBinding.answerUserInput.text.toString().trim()
        val correctAnswer = currentLevelData?.correctAnswer

        if(userAnswer.equals(correctAnswer, ignoreCase = true)) {

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
}