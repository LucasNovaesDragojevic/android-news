package com.news.di.modules

import androidx.room.Room
import com.news.database.AppDatabase
import com.news.repository.NewsRepository
import com.news.retrofit.webclient.NewsWebClient
import com.news.ui.viewmodel.FormNewsViewModel
import com.news.ui.viewmodel.ListNewsViewModel
import com.news.ui.viewmodel.ViewNewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DATA_BASE_NAME = "news.db"

val appModules = module {
    single {
        Room.databaseBuilder(get() , AppDatabase::class.java, DATA_BASE_NAME).build()
    }
    single {
        get<AppDatabase>().newsDao
    }
    single {
        NewsWebClient()
    }
    single {
        NewsRepository(get(), get())
    }
    viewModel {
        ListNewsViewModel(get())
    }
    viewModel {
        (id: Long) -> ViewNewsViewModel(id, get())
    }
    viewModel {
        FormNewsViewModel(get())
    }
}