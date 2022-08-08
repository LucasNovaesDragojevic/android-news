package com.news.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.news.R
import com.news.model.News
import com.news.ui.activity.NEWS_KEY
import com.news.ui.fragment.extensions.showError
import com.news.ui.viewmodel.ViewNewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val NEWS_NOT_FOUND = "News not found"
private const val FAIL_ON_REMOVE_NEWS = "Unable to remove news"
class NewsViewFragment : Fragment(R.layout.news_view) {

    private val newsId by lazy {
        arguments?.getLong(NEWS_KEY) ?: throw IllegalArgumentException("Invalid ID")
    }

    private val viewModel: ViewNewsViewModel by viewModel {
        Log.i("NEWS_APP", "newsId = $newsId")
        parametersOf(newsId)
    }

    var whenFinish: () -> Unit = {}

    var whenSelectEditMenu: (newsSelected: News) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        verifyNewsId()
        findNewsSelected()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_view, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.view_news_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.visualiza_noticia_menu_edita -> {
                viewModel.newsFound.value?.let(whenSelectEditMenu)
            }
            R.id.visualiza_noticia_menu_remove -> remove()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun findNewsSelected() {
        viewModel.newsFound.observe(this) {
            it?.let(this::fillFields)
        }
    }

    private fun verifyNewsId() {
        if (newsId == 0L) {
            showError(NEWS_NOT_FOUND)
            whenFinish()
        }
    }

    private fun fillFields(news: News) {
        Log.i("NEWS_APP", "$news")
        view?.findViewById<TextView>(R.id.news_view_title)?.text = news.title
        view?.findViewById<TextView>(R.id.news_view_text)?.text = news.text
    }

    fun remove() {
        viewModel.remove().observe(this) {
            if (it.error == null)
                whenFinish()
            else
                showError(FAIL_ON_REMOVE_NEWS)
        }
    }
}

