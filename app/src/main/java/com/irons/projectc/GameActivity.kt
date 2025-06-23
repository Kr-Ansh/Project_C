package com.irons.projectc

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.irons.projectc.MainActivity
import com.irons.projectc.chapters.Level0Fragment
import com.irons.projectc.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    lateinit var gameBinding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        gameBinding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(gameBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //***************** Calls Level0Fragment ********************
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragLevel0 = Level0Fragment()

        fragmentTransaction.add(R.id.frameLayout, fragLevel0)
        fragmentTransaction.commit()
        //************************************************************

    }
}