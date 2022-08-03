package com.news.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.news.R
import com.news.databinding.ActivityFormNewsBinding
import com.news.model.News
import com.news.ui.activity.extensions.showError
import com.news.ui.viewmodel.FormNewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ERROR_ON_FAIL = "Cannot possible to save the news."

class FormNewsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormNewsBinding.inflate(layoutInflater)
    }

    private val newsId by lazy {
        intent.getLongExtra(NEWS_KEY, 0)
    }

    private val viewModel: FormNewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configTitle()
        fillForm()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.form_news_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.form_news_save -> {
                val title = binding.activityFormNewsTitle.text.toString()
                val text = binding.activityFormNewsText.text.toString()
                save(News(newsId, title, text))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configTitle() {
        title = if (newsId > 0 ) "Edit" else "Create"
    }

    private fun fillForm() {
        viewModel.findById(newsId)
            .observe(this) {
                if (it != null) {
                    binding.activityFormNewsTitle.setText(it.title)
                    binding.activityFormNewsText.setText(it.text)
                }
            }
    }

    private fun save(news: News) {
        viewModel.save(news)
            .observe(this) {
                if (it.error == null)
                    finish()
                else
                    showError(ERROR_ON_FAIL)
            }
    }
}