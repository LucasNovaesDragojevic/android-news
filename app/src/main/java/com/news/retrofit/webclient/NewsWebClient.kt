package com.news.retrofit.webclient

import com.news.model.News
import com.news.retrofit.AppRetrofit
import com.news.retrofit.service.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val FAIL_ON_REQUEST = "Fail on request"

class NewsWebClient(
    private val service: NewsService = AppRetrofit().newsService
) {

    private fun <T> executeRequest(call: Call<T>, whenSuccess: (newsCreated: T?) -> Unit, whenFail: (error: String?) -> Unit) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    whenSuccess(response.body())
                } else {
                    whenFail(FAIL_ON_REQUEST)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                whenFail(t.message)
            }
        })
    }

    fun findAll(whenSuccess: (newsCreated: List<News>?) -> Unit, whenFail: (error: String?) -> Unit) = executeRequest(service.findAll(), whenSuccess, whenFail)

    fun save(news: News, whenSuccess: (newsCreated: News?) -> Unit, whenFail: (error: String?) -> Unit) = executeRequest(service.save(news), whenSuccess, whenFail)

    fun edit(id: Long, news: News, whenSuccess: (newsCreated: News?) -> Unit, whenFail: (error: String?) -> Unit) = executeRequest(service.edit(id, news), whenSuccess, whenFail)

    fun remove(id: Long, whenSuccess: (newsCreated: Void?) -> Unit, whenFail: (error: String?) -> Unit) = executeRequest(service.remove(id), whenSuccess, whenFail)
}
