package com.news.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.news.repository.NewsRepository
import com.news.ui.viewmodel.FormNewsViewModel

class FormNewsViewModelFactory(
    private val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = FormNewsViewModel(newsRepository) as T
}