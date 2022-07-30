package com.news.ui.activity.extensions

import android.app.Activity
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText

fun Activity.showError(message: String) {
    makeText(this, message, LENGTH_LONG).show()
}