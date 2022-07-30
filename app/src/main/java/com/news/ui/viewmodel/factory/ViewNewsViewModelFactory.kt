package com.news.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.news.repository.NewsRepository
import com.news.ui.viewmodel.ViewNewsViewModel

class ViewNewsViewModelFactory(
    private val id: Long,
    private val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ViewNewsViewModel(id, newsRepository) as T
}
