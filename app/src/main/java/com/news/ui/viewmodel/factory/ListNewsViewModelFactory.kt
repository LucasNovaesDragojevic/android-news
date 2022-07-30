package com.news.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.news.repository.NewsRepository
import com.news.ui.viewmodel.ListNewsViewModel

@Suppress("UNCHECKED_CAST")
class ListNewsViewModelFactory(
    private val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListNewsViewModel(newsRepository) as T
    }
}