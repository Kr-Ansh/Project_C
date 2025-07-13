package com.irons.projectc.levels

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.irons.projectc.R
import com.irons.projectc.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {

    lateinit var webBinding: ActivityWebBinding

    private val PREFS_NAME = "WebViewPrefs"
    private val LAST_URL_KEY = "lastUrl"

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

        // So that google.com will only load on startup (as I was facing some bugs before)
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(LAST_URL_KEY, "https://www.google.com/")
            apply()
        }

        webViewSetUp(webBinding.webView)

        webBinding.btnBack.setOnClickListener {
            finish()
        }
        webBinding.btnNotes.setOnClickListener {
            val intent = Intent(this@WebActivity, NotesActivity::class.java)
            startActivity(intent)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webBinding.webView.canGoBack()) {
                    webBinding.webView.goBack()
                } else {
                    isEnabled = false
                    finish()
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetUp(webView: WebView){
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                url?.let {
                    saveLastUrl(it)
                }
            }
        }
        webView.apply {
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true

            val lastUrl = getLastUrl()
            if(lastUrl != null) {
                loadUrl(lastUrl)
            } else {
                loadUrl("https://www.google.com/")
            }
        }
    }

    private fun saveLastUrl(url: String) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(LAST_URL_KEY, url)
            apply()
        }
    }

    private fun getLastUrl(): String? {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(LAST_URL_KEY, null)
    }

    override fun onPause() {
        super.onPause()
        webBinding.webView.url?.let {
            saveLastUrl(it)
        }
    }

    override fun onDestroy() {
        // Proper WebView cleanup to prevent memory leaks
        (webBinding.webView.parent as? android.view.ViewGroup)?.removeView(webBinding.webView)
        webBinding.webView.destroy()
        super.onDestroy()
    }
}