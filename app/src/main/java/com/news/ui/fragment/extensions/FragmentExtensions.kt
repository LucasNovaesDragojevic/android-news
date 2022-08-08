package com.news.ui.fragment.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showError(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}