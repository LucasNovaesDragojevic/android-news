package com.news.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.news.R
import com.news.databinding.ActivityNewsViewBinding
import com.news.model.News
import com.news.ui.activity.extensions.showError
import com.news.ui.viewmodel.ViewNewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val NEWS_NOT_FOUND = "News not found"
private const val FAIL_ON_REMOVE_NEWS = "Unable to remove news"

class NewsViewActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNewsViewBinding.inflate(layoutInflater)
    }

    private val newsId by lazy {
        intent.getLongExtra(NEWS_KEY, 0)
    }

    private val viewModel: ViewNewsViewModel by viewModel { parametersOf(newsId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        verifyNewsId()
        findNewsSelected()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.view_news_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.visualiza_noticia_menu_edita -> openEditForm()
            R.id.visualiza_noticia_menu_remove -> remove()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun remove() {
        viewModel.remove()
            .observe(this) {
                if (it.error == null)
                    finish()
                else
                    showError(FAIL_ON_REMOVE_NEWS)
        }
    }

    private fun openEditForm() {
        val intent = Intent(this, FormNewsActivity::class.java)
        intent.putExtra(NEWS_KEY, newsId)
        startActivity(intent)
    }

    private fun findNewsSelected() {
        viewModel.newsFinded.observe(this) {
            it?.let(this::fillFields)
        }
    }

    private fun fillFields(news: News) {
        binding.activityNewsViewTitle.text = news.title
        binding.activityNewsViewText.text = news.text
    }

    private fun verifyNewsId() {
        if (newsId == 0L) {
            showError(NEWS_NOT_FOUND)
            finish()
        }
    }

}