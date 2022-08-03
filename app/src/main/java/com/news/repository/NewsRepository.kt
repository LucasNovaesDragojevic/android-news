package com.news.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.news.asynctask.BaseAsyncTask
import com.news.database.dao.NewsDao
import com.news.model.News
import com.news.retrofit.webclient.NewsWebClient

class NewsRepository(
    private val newsDao: NewsDao,
    private val webclient: NewsWebClient
) {

    private val mediator = MediatorLiveData<Resource<List<News>?>>()

    fun findAll() : LiveData<Resource<List<News>?>> {
        mediator.addSource(findInDB()) {
            mediator.value = Resource(it)
        }
        val failsFromWebApi = MutableLiveData<Resource<List<News>?>>()
        mediator.addSource(failsFromWebApi) { resourceOfFail ->
            val actualResource = mediator.value
            val newResource: Resource<List<News>?> = if (actualResource != null)
                Resource(resourceOfFail.data, resourceOfFail.error)
            else
                resourceOfFail
            mediator.value = newResource
        }
        findIdAPI(whenFail = { error ->
            failsFromWebApi.value = Resource(null, error)
        })
        return mediator
    }

    fun save(news: News): LiveData<Resource<Void?>> {
        val liveData = MutableLiveData<Resource<Void?>>()
        saveInAPI(news,
            whenSuccess = {
                liveData.value = Resource(null)
            }, whenFail = {
                liveData.value = Resource(null, it)
            })
        return liveData
    }

    fun remove(news: News): LiveData<Resource<Void?>> {
        val liveData = MutableLiveData<Resource<Void?>>()
        removeFromAPI(news, {
            liveData.value = Resource(null)
        },{
            liveData.value = Resource(null, it)
        })
        return liveData
    }

    fun edit(news: News): LiveData<Resource<Void?>> {
        val liveData = MutableLiveData<Resource<Void?>>()
        editInAPI(news, {
            liveData.value = Resource(null)
        }, {
            liveData.value = Resource(null, it)
        })
        return liveData
    }

    fun findByID(id: Long): LiveData<News?> = newsDao.findById(id)

    private fun findIdAPI(whenFail: (error: String?) -> Unit) {
        webclient.findAll(
            whenSuccess = { newsCreated ->
                newsCreated?.let {
                    saveInDB(newsCreated)
                }
            }, whenFail = whenFail
        )
    }

    private fun findInDB(): LiveData<List<News>> = newsDao.findAll()

    private fun saveInAPI(news: News, whenSuccess: () -> Unit, whenFail: (error: String?) -> Unit) {
        webclient.save(news, {
            it?.let {
                saveInDB(it, whenSuccess)
            }
        }, whenFail )
    }

    private fun saveInDB(news: List<News>) {
        BaseAsyncTask({
            newsDao.save(news)
        }, {}).execute()
    }

    private fun saveInDB(news: News, whenSuccess: () -> Unit) {
        BaseAsyncTask({
            newsDao.save(news)
        }, {
            whenSuccess()
        }).execute()
    }

    private fun removeFromAPI(news: News, whenSuccess: () -> Unit, whenFail: (error: String?) -> Unit) = webclient.remove(news.id, { removeFromDB(news, whenSuccess) }, whenFail)

    private fun removeFromDB(news: News, whenSuccess: () -> Unit) {
        BaseAsyncTask({
            newsDao.remove(news)
        }, {
            whenSuccess()
        }).execute()
    }

    private fun editInAPI(news: News, whenSuccess: () -> Unit, whenFail: (error: String?) -> Unit) {
        webclient.edit(news.id, news, {
            it?.let {
                saveInDB(it, whenSuccess)
            }
        }, whenFail )
    }
}