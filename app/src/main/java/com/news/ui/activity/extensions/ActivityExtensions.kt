package com.news.ui.activity.extensions

import android.app.Activity
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

fun Activity.showError(message: String) {
    makeText(this, message, LENGTH_LONG).show()
}

fun AppCompatActivity.fragmentTransaction(execute: FragmentTransaction.() -> Unit) {
    val transaction = supportFragmentManager.beginTransaction()
    execute(transaction)
    transaction.commit()
}