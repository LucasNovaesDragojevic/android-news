package com.news.asynctask

import android.os.AsyncTask

class BaseAsyncTask<T>(
    private val executed: () -> T,
    private val finally: (result: T) -> Unit
) : AsyncTask<Void, Void, T>() {

    @Deprecated("Deprecated in Java", ReplaceWith("executed()"))
    override fun doInBackground(vararg params: Void?) = executed()

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: T) {
        super.onPostExecute(result)
        finally(result)
    }

}