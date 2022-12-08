package com.sangmeebee.searchmovieproject.ui.base

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val uri = request.url
        return if (uri != null) {
            view.scrollTo(0, 0)
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