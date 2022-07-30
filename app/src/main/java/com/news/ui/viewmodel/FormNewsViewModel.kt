package com.news.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.news.model.News
import com.news.repository.NewsRepository
import com.news.repository.Resource

class FormNewsViewModel (
    private val repository: NewsRepository
) : ViewModel() {

    fun findById(id: Long) = repository.findByID(id)

    fun save(news: News): LiveData<Resource<Void?>> {
        return if (news.id > 0)
            repository.edit(news)
        else
            repository.save(news)
    }
}