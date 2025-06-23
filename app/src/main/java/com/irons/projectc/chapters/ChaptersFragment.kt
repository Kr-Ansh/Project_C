package com.irons.projectc.chapters

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.irons.projectc.GameActivity
import com.irons.projectc.R
import com.irons.projectc.databinding.FragmentChaptersBinding
import com.irons.projectc.levels.Levels0Fragment

class ChaptersFragment : Fragment() {

    private lateinit var fragChapterBinding: FragmentChaptersBinding

    private lateinit var gameActivity: GameActivity

    var isLevel1Completed = false
    var isLevel2Completed = false
    var isLevel3Completed = false
    var isLevel4Completed = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragChapterBinding = FragmentChaptersBinding.inflate(inflater, container, false)

        gameActivity = activity as GameActivity

        fragChapterBinding.btn01.setOnClickListener {

            gameActivity.gameBinding.tvGameTitle.setText(R.string.level_title_0_1)

            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager // We can't use supportFragmentManager directly
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayout, Levels0Fragment())
            fragmentTransaction.commit()
        }


        return fragChapterBinding.root
    }

    override fun onStart() {
        super.onStart()

        //********************** Animations **********************************
        val animator = AnimatorInflater.loadAnimator(context, R.animator.button_move_left) as ObjectAnimator
        animator.target = fragChapterBinding.btn01
        animator.start()

        val animator2 = AnimatorInflater.loadAnimator(context, R.animator.button_move_right) as ObjectAnimator
        animator2.target = fragChapterBinding.btn03
        animator2.start()

        val animator3 = AnimatorInflater.loadAnimator(context, R.animator.button_move_up) as ObjectAnimator
        animator3.target = fragChapterBinding.btn02
        animator3.start()

        val animator4 = AnimatorInflater.loadAnimator(context, R.animator.button_move_down) as ObjectAnimator
        animator4.target = fragChapterBinding.btn04
        animator4.start()
        //*********************************************************************
    }
}