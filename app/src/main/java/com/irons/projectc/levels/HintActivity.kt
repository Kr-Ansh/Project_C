package com.irons.projectc.levels

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.irons.projectc.R
import com.irons.projectc.databinding.ActivityHintBinding

class HintActivity : AppCompatActivity() {

    lateinit var hintBinding: ActivityHintBinding

    private var mediaPlayer: MediaPlayer?= null

    var rewardedAd: RewardedAd ?= null
    var adsCount = 10

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

        mediaPlayer = MediaPlayer.create(this, R.raw.btn_sound)

        loadAds()

        hintBinding.btnBack.setOnClickListener {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                    prepare()
                }
                start()
            }
            finish()
        }
        hintBinding.btnNotes.setOnClickListener {
            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                    prepare()
                }
                start()
            }
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
        }

        val currentChapterNo = intent.getIntExtra("currentChapterNo", -1)
        val currentLevelNo = intent.getIntExtra("currentLevelNo", -1)

        loadHints(currentChapterNo, currentLevelNo)

        hintBinding.btnShowAd.setOnClickListener { // Code for Ads logic

            mediaPlayer?.apply {
                if (isPlaying) {
                    stop()
                    prepare()
                }
                start()
            }

            rewardedAd?.show(
                this,
                OnUserEarnedRewardListener { rewardItem ->
                    // Handle the reward.
                    adsCount += 1
                    Toast.makeText(this@HintActivity, "Ads Viewed: $adsCount",
                        Toast.LENGTH_SHORT).show()
                },
            )
            rewardedAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed
                        loadAds()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        // Called when ad fails to show
                        Toast.makeText(this@HintActivity, "Some error occurred, Please try again later\nAds Viewed: $adsCount",
                            Toast.LENGTH_SHORT).show()
                        rewardedAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                    }

                    override fun onAdImpression() {
                        // Called when an impression is recorded for an ad.
                    }

                    override fun onAdClicked() {
                        // Called when an ad is clicked.
                    }
                }
        }
    }

    fun loadAds() {
        RewardedAd.load( // dummy adUnitId = "ca-app-pub-3940256099942544/5224354917", my adUnitId = "ca-app-pub-1262945574886048/5642047133"
            this,
            "ca-app-pub-3940256099942544/5224354917",
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the error
                    Toast.makeText(this@HintActivity, "Some error occurred, Please try again later\nAds Viewed: $adsCount",
                        Toast.LENGTH_SHORT).show()
                    rewardedAd = null
                }
            },
        )
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