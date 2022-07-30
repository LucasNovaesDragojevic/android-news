package com.news.retrofit.service

import com.news.model.News
import retrofit2.Call
import retrofit2.http.*

interface NewsService {

    @GET("noticias")
    fun findAll(): Call<List<News>>

    @POST("noticias")
    fun save(@Body news: News): Call<News>

    @PUT("noticias/{id}")
    fun edit(@Path("id") id: Long, @Body news: News) : Call<News>

    @DELETE("noticias/{id}")
    fun remove(@Path("id") id: Long): Call<Void>

}
