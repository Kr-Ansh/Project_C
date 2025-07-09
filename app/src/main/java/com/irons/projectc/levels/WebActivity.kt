package com.irons.projectc.levels

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.irons.projectc.R
import com.irons.projectc.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {

    lateinit var webBinding: ActivityWebBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        webBinding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(webBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        webViewSetUp(webBinding.webView)

        webBinding.btnBack.setOnClickListener {
            finish()
        }
        webBinding.btnNotes.setOnClickListener {
            val intent = Intent(this@WebActivity, NotesActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetUp(a: WebView){
        a.webViewClient = WebViewClient()
        a.apply {
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
            loadUrl("https://www.google.com/")
        }
    }
}