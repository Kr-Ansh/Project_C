package com.irons.projectc.levels

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.irons.projectc.R
import com.irons.projectc.chapters.Chapter0Activity
import com.irons.projectc.databinding.ActivityLevels0Binding

class Levels0Activity : AppCompatActivity() {

    lateinit var levels0Binding: ActivityLevels0Binding

    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("stats")

    val answer01 = "Clue.dicode"
    val answer02 = "interesting-morse"
    val answer03 = "mahabharata"
    val answer04 = "KrAnsh"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        levels0Binding = ActivityLevels0Binding.inflate(layoutInflater)
        setContentView(levels0Binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var lvlNo = intent.getIntExtra("chapter0", 0)

        if(lvlNo == 1) {
            levels0Binding.tvLevelTitle.setText(R.string.level_title_0_1)
            levels0Binding.tvAboutLevel.setText(R.string.level_description_0_1)
            levels0Binding.tvQuestion.setText(R.string.level_question_0_1)

            levels0Binding.buttonSubmit.setOnClickListener {

                if(levels0Binding.answerUserInput.text.toString().trim() == answer01){
                    ref.child("lvl01stat").setValue(true)
                    levels0Binding.tvAboutLevel.text = "Q"
                    levels0Binding.tvQuestion.setText(R.string.go_back_text)
                    Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
                }
            }
        } else if(lvlNo == 2) {
            levels0Binding.tvLevelTitle.setText(R.string.level_title_0_2)
            levels0Binding.tvAboutLevel.setText(R.string.level_description_0_2)
            levels0Binding.tvQuestion.setText(R.string.level_question_0_2)

            levels0Binding.buttonSubmit.setOnClickListener {

                if(levels0Binding.answerUserInput.text.toString().lowercase().trim() == answer02){
                    ref.child("lvl02stat").setValue(true)
                    levels0Binding.tvAboutLevel.text = "Y"
                    levels0Binding.tvQuestion.setText(R.string.go_back_text)
                    Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
                }
            }
        } else if(lvlNo == 3) {
            levels0Binding.tvLevelTitle.setText(R.string.level_title_0_3)
            levels0Binding.tvAboutLevel.setText(R.string.level_description_0_3)
            levels0Binding.tvQuestion.setText(R.string.level_question_0_3)

            levels0Binding.buttonSubmit.setOnClickListener {

                if(levels0Binding.answerUserInput.text.toString().lowercase().trim() == answer03){
                    ref.child("lvl03stat").setValue(true)
                    levels0Binding.tvAboutLevel.text = "N"
                    levels0Binding.tvQuestion.setText(R.string.go_back_text)
                    Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
                }
            }
        } else if(lvlNo == 4) {
            levels0Binding.tvLevelTitle.setText(R.string.level_title_0_4)
            levels0Binding.tvAboutLevel.setText(R.string.level_description_0_4)
            levels0Binding.tvQuestion.setText(R.string.level_question_0_4)

            levels0Binding.buttonSubmit.setOnClickListener {

                if(levels0Binding.answerUserInput.text.toString().trim() == answer04){
                    ref.child("lvl04stat").setValue(true)
                    levels0Binding.tvAboutLevel.text = "Z"
                    levels0Binding.tvQuestion.setText(R.string.go_back_text)
                    Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}