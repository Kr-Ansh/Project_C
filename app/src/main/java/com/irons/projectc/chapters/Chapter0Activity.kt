package com.irons.projectc.chapters

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.irons.projectc.R
import com.irons.projectc.databinding.ActivityChapter0Binding
import com.irons.projectc.levels.Levels0Activity

class Chapter0Activity : AppCompatActivity() {

    lateinit var chapter0Binding: ActivityChapter0Binding

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

        chapter0Binding.btn01.setOnClickListener {
            val intent = Intent(this@Chapter0Activity, Levels0Activity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        val animator = AnimatorInflater.loadAnimator(this@Chapter0Activity, R.animator.button_move_left) as ObjectAnimator
        animator.target = chapter0Binding.btn01
        animator.start()

        val animator2 = AnimatorInflater.loadAnimator(this@Chapter0Activity, R.animator.button_move_up) as ObjectAnimator
        animator2.target = chapter0Binding.btn02
        animator2.start()

        val animator3 = AnimatorInflater.loadAnimator(this@Chapter0Activity, R.animator.button_move_right) as ObjectAnimator
        animator3.target = chapter0Binding.btn03
        animator3.start()

        val animator4 = AnimatorInflater.loadAnimator(this@Chapter0Activity, R.animator.button_move_down) as ObjectAnimator
        animator4.target = chapter0Binding.btn04
        animator4.start()

    }
}