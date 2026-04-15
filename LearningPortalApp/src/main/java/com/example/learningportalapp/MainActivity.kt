package com.example.learningportalapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar
    lateinit var etUrl: EditText

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        etUrl = findViewById(R.id.etUrl)
        progressBar.visibility = ProgressBar.GONE

        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnForward = findViewById<Button>(R.id.btnForward)
        val btnRefresh = findViewById<Button>(R.id.btnRefresh)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnGo = findViewById<Button>(R.id.btnGo)

        val btnGoogle = findViewById<Button>(R.id.btnGoogle)
        val btnYoutube = findViewById<Button>(R.id.btnYoutube)
        val btnWiki = findViewById<Button>(R.id.btnWiki)
        val btnKhan = findViewById<Button>(R.id.btnKhan)
        val btnUniversity = findViewById<Button>(R.id.btnUniversity)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        progressBar.max = 100

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                progressBar.visibility = ProgressBar.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = ProgressBar.GONE
                etUrl.setText(url)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                webView.loadUrl("file:///android_asset/offline.html")
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
            }
        }

        webView.loadUrl("https://www.google.com")

        btnGo.setOnClickListener {
            var url = etUrl.text.toString()
            if (!url.startsWith("http")) {
                url = "https://$url"
            }
            webView.loadUrl(url)
        }

        etUrl.setOnEditorActionListener { _, _, _ ->
            var url = etUrl.text.toString()
            if (!url.startsWith("http")) {
                url = "https://$url"
            }
            webView.loadUrl(url)
            true
        }

        btnBack.setOnClickListener {
            if (webView.canGoBack()) webView.goBack()
            else Toast.makeText(this, "No more history", Toast.LENGTH_SHORT).show()
        }

        btnForward.setOnClickListener {
            if (webView.canGoForward()) webView.goForward()
        }

        btnRefresh.setOnClickListener {
            webView.reload()
        }

        btnUniversity.setOnClickListener {
            webView.loadUrl("https://www.aiub.edu")
        }

        btnGoogle.setOnClickListener {
            webView.loadUrl("https://www.google.com")
        }


        btnYoutube.setOnClickListener {
            webView.loadUrl("https://www.youtube.com")
        }

        btnWiki.setOnClickListener {
            webView.loadUrl("https://www.wikipedia.org")
        }

        btnKhan.setOnClickListener {
            webView.loadUrl("https://www.khanacademy.org")
        }


    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }
}