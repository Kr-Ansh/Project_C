package com.irons.projectc.levels

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.irons.projectc.R
import com.irons.projectc.databinding.ActivityHintBinding

class HintActivity : AppCompatActivity() {

    lateinit var hintBinding: ActivityHintBinding

    var adsCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hintBinding = ActivityHintBinding.inflate(layoutInflater)
        setContentView(hintBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        hintBinding.btnBack.setOnClickListener {
            finish()
        }
        hintBinding.btnNotes.setOnClickListener {
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
        }

        val currentChapterNo = intent.getIntExtra("currentChapterNo", -1)
        val currentLevelNo = intent.getIntExtra("currentLevelNo", -1)

        loadHints(currentChapterNo, currentLevelNo)

        hintBinding.btnShowAd?.setOnClickListener {
            adsCount += 1
        }
    }

    private fun loadHints(currentChapterNo: Int, currentLevelNo: Int) {

        when (Pair(currentChapterNo, currentLevelNo)) {

            Pair(0, 1) -> {
                hintBinding.tvHint2?.isVisible = false
                hintBinding.tvHint3?.isVisible = false
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_0_1_hint)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_0_1_answer)
                    }
                }
            }
            Pair(0, 2) -> {
                hintBinding.tvHint2?.isVisible = false
                hintBinding.tvHint3?.isVisible = false
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_0_2_hint)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_0_2_answer)
                    }
                }
            }
            Pair(0, 3) -> {
                hintBinding.tvHint2?.isVisible = false
                hintBinding.tvHint3?.isVisible = false
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_0_3_hint)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_0_3_answer)
                    }
                }
            }
            Pair(0, 4) -> {
                hintBinding.tvHint2?.isVisible = false
                hintBinding.tvHint3?.isVisible = false
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_0_4_hint)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_0_4_answer)
                    }
                }
            }
            Pair(1, 1) -> {
                hintBinding.tvHint3?.isVisible = false
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_1_1_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_1_1_hint2)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_1_1_answer)
                    }
                }
            }
            Pair(1, 2) -> {
                hintBinding.tvHint3?.isVisible = false
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_1_2_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_1_2_hint2)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_1_2_answer)
                    }
                }
            }
            Pair(1, 3) -> {
                hintBinding.tvHint3?.isVisible = false
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_1_3_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_1_3_hint2)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_1_3_answer)
                    }
                }
            }
            Pair(1, 4) -> {
                hintBinding.tvHint3?.isVisible = false
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_1_4_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_1_4_hint2)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_1_4_answer)
                    }
                }
            }
            Pair(2, 1) -> {
                hintBinding.tvHint3?.isVisible = false
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_2_1_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_2_1_hint2)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_2_1_answer)
                    }
                }
            }
            Pair(2, 2) -> {
                hintBinding.tvHint3?.isVisible = false
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_2_2_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_2_2_hint2)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_2_2_answer)
                    }
                }
            }
            Pair(2, 3) -> {
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_2_3_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_2_3_hint2)
                    }
                }
                hintBinding.tvHint3?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint3!!.setText(R.string.level_2_3_hint3)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 4) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/4", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_2_3_answer)
                    }
                }
            }
            Pair(2, 4) -> {
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_2_4_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_2_4_hint2)
                    }
                }
                hintBinding.tvHint3?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint3!!.setText(R.string.level_2_4_hint3)
                    }
                }
                hintBinding.tvHint4?.setOnClickListener {
                    if(adsCount < 4) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/4", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint4!!.setText(R.string.level_2_4_hint4)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 5) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/5", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_2_4_answer)
                    }
                }
            }
            Pair(3, 1) -> {
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_3_1_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_3_1_hint2)
                    }
                }
                hintBinding.tvHint3?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint3!!.setText(R.string.level_3_1_hint3)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 4) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/4", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_3_1_answer)
                    }
                }
            }
            Pair(3, 2) -> {
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_3_2_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_3_2_hint2)
                    }
                }
                hintBinding.tvHint3?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint3!!.setText(R.string.level_3_2_hint3)
                    }
                }
                hintBinding.tvHint4?.setOnClickListener {
                    if(adsCount < 4) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/4", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint4!!.setText(R.string.level_3_2_hint4)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 5) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/5", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_3_2_answer)
                    }
                }
            }
            Pair(3, 3) -> {
                hintBinding.tvHint3?.isVisible = false
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_3_3_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_3_3_hint2)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_3_3_answer)
                    }
                }
            }
            Pair(3, 4) -> {
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_3_4_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_3_4_hint2)
                    }
                }
                hintBinding.tvHint3?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint3!!.setText(R.string.level_3_4_hint3)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 4) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/4", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_3_4_answer)
                    }
                }
            }
            Pair(4, 1) -> {
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_4_1_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_4_1_hint2)
                    }
                }
                hintBinding.tvHint3?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint3!!.setText(R.string.level_4_1_hint3)
                    }
                }
                hintBinding.tvHint4?.setOnClickListener {
                    if(adsCount < 4) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/4", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint4!!.setText(R.string.level_4_1_hint4)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 5) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/5", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_4_1_answer)
                    }
                }
            }
            Pair(4, 2) -> {
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_4_2_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_4_2_hint2)
                    }
                }
                hintBinding.tvHint3?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint3!!.setText(R.string.level_4_2_hint3)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 4) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/4", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_4_2_answer)
                    }
                }
            }
            Pair(4, 3) -> {
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvHint5?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_4_3_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_4_3_hint2)
                    }
                }
                hintBinding.tvHint3?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint3!!.setText(R.string.level_4_3_hint3)
                    }
                }
                hintBinding.tvHint4?.setOnClickListener {
                    if(adsCount < 4) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/4", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint4!!.setText(R.string.level_4_3_hint4)
                    }
                }
                hintBinding.tvHint5?.setOnClickListener {
                    if(adsCount < 5) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/5", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint5!!.setText(R.string.level_4_3_hint5)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 6) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/6", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_4_1_answer)
                    }
                }
            }
            Pair(4, 4) -> {
                hintBinding.tvHint4?.isVisible = false
                hintBinding.tvHint5?.isVisible = false
                hintBinding.tvAnswer2?.isVisible = false
                hintBinding.tvAnswer3?.isVisible = false

                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_4_4_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_4_4_hint2)
                    }
                }
                hintBinding.tvHint3?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint3!!.setText(R.string.level_4_4_hint3)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 4) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/4", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_4_4_answer)
                    }
                }
            }
            Pair(5, 0) -> {
                hintBinding.tvHint1!!.setOnClickListener {
                    if(adsCount < 1) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/1", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint1!!.setText(R.string.level_5_0_hint)
                    }
                }
                hintBinding.tvHint2?.setOnClickListener {
                    if(adsCount < 2) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/2", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint2!!.setText(R.string.level_5_0_hint2)
                    }
                }
                hintBinding.tvHint3?.setOnClickListener {
                    if(adsCount < 3) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/3", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint3!!.setText(R.string.level_5_0_hint3)
                    }
                }
                hintBinding.tvHint4?.setOnClickListener {
                    if(adsCount < 4) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/4", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint4!!.setText(R.string.level_5_0_hint4)
                    }
                }
                hintBinding.tvHint5?.setOnClickListener {
                    if(adsCount < 5) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/5", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvHint5!!.setText(R.string.level_5_0_hint5)
                    }
                }
                hintBinding.tvAnswer?.setOnClickListener {
                    if(adsCount < 6) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/6", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer!!.setText(R.string.level_5_0_answer)
                    }
                }
                hintBinding.tvAnswer2?.setOnClickListener {
                    if(adsCount < 7) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/7", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer2!!.setText(R.string.level_5_0_answer2)
                    }
                }
                hintBinding.tvAnswer3?.setOnClickListener {
                    if(adsCount < 8) {
                        Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount/8", Toast.LENGTH_SHORT).show()
                    } else {
                        hintBinding.tvAnswer3!!.setText(R.string.level_5_0_answer3)
                    }
                }
            }
        }
    }
}