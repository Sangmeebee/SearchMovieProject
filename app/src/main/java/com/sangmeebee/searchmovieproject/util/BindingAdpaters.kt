package com.sangmeebee.searchmovieproject.util

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load

@BindingAdapter("imageUrl")
fun ImageView.setImageByUrl(url: String) {
    load(url) {
        crossfade(true)
    }
}

@BindingAdapter("htmlText")
fun TextView.setHtmlText(text: String) {
    setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())
}

@BindingAdapter(value = ["hideKeyboard", "onEditorActionEvent"], requireAll = false)
fun EditText.setOnEditorActionEvent(hideKeyboard: Boolean, onActionEvent: ((String) -> Unit)?) {
    setOnEditorActionListener { _, actionId, _ ->
        if (hideKeyboard) {
            SoftInputUtil(context).hideKeyboard(this)
            clearFocus()
        }
        return@setOnEditorActionListener when (actionId) {
            EditorInfo.IME_ACTION_DONE -> {
                onActionEvent?.let { it(text.toString()) }
                true
            }
            else -> false
        }
    }
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.isRefreshing(isRefreshing: Boolean) {
    this.isRefreshing = isRefreshing
}
