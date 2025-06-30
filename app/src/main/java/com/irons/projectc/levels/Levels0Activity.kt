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

        levels0Binding.buttonSubmit.setOnClickListener {

            if(levels0Binding.answerUserInput.text.toString() == "Clue.dicode"){
                ref.child("lvl01stat").setValue(true)
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            }

        }
    }
}