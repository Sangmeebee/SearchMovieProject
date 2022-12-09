package com.sangmeebee.searchmovieproject.presentation.util

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load

@BindingAdapter("imageUrl")
fun ImageView.setImageByUrl(url: String) {
    load(url) {
        crossfade(true)
    }
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
