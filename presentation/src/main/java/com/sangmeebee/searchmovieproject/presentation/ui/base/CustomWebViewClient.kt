package com.sangmeebee.searchmovieproject.presentation.ui.base

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val uri = request.url
        return if (uri != null) {
            false
        } else {
            view.loadUrl(BASE_URL)
            true
        }
    }

    companion object {
        const val BASE_URL = "https://www.naver.com"
    }
}
