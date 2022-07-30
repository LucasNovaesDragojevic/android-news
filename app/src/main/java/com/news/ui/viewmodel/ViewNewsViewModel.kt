package com.news.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.news.repository.NewsRepository
import com.news.repository.Resource

class ViewNewsViewModel (
    val id: Long,
    private val newsRepository: NewsRepository
) : ViewModel() {

    val newsFinded = newsRepository.findByID(id)

    fun remove(): LiveData<Resource<Void?>> {
        return newsFinded.value?.run {
            newsRepository.remove(this)
        } ?: MutableLiveData<Resource<Void?>>().also {
            it.value = Resource(null, "News not found")
        }
    }
}
