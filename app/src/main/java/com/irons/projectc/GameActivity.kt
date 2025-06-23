package com.irons.projectc

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.irons.projectc.chapters.ChaptersFragment
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

        //***************** Calls ChaptersFragment ********************
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val chaptersFragment = ChaptersFragment()

        fragmentTransaction.add(R.id.frameLayout, chaptersFragment)
        fragmentTransaction.commit()
        //************************************************************

    }
}