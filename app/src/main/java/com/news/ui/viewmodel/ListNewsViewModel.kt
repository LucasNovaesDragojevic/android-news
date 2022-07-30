package com.news.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.news.repository.NewsRepository

class ListNewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    fun findAll() = newsRepository.findAll()
}