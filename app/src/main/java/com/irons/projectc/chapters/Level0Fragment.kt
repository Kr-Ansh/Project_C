package com.irons.projectc.chapters

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.irons.projectc.MainActivity
import com.irons.projectc.R
import com.irons.projectc.databinding.FragmentLevel0Binding

class Level0Fragment : Fragment() {

    lateinit var fragLevel0Binding: FragmentLevel0Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragLevel0Binding = FragmentLevel0Binding.inflate(inflater, container, false)



        return fragLevel0Binding.root
    }

    // Animation for buttons
    override fun onStart() {
        super.onStart()

        val animator = AnimatorInflater.loadAnimator(context, R.animator.button_move_left) as ObjectAnimator
        animator.target = fragLevel0Binding.btn01
        animator.start()

        val animator2 = AnimatorInflater.loadAnimator(context, R.animator.button_move_right) as ObjectAnimator
        animator2.target = fragLevel0Binding.btn03
        animator2.start()

        val animator3 = AnimatorInflater.loadAnimator(context, R.animator.button_move_up) as ObjectAnimator
        animator3.target = fragLevel0Binding.btn02
        animator3.start()

        val animator4 = AnimatorInflater.loadAnimator(context, R.animator.button_move_down) as ObjectAnimator
        animator4.target = fragLevel0Binding.btn04
        animator4.start()
    }
}