package com.news.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.news.databinding.ActivityListNewsBinding
import com.news.model.News
import com.news.ui.activity.extensions.showError
import com.news.ui.recyclerview.adapter.ListNewsAdapter
import com.news.ui.viewmodel.ListNewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListNewsActivity : AppCompatActivity() {

    private val activityListNewsBinding by lazy {
        ActivityListNewsBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListNewsAdapter(context = this)
    }

    private val viewModel: ListNewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityListNewsBinding.root)
        configRecyclerView()
        configFabAddNews()
        findNews()
    }

    private fun findNews() {
        viewModel.findAll().observe(this) { resource ->
            resource.data?.let { adapter.update(it) }
            resource.error?.let { showError(FAIL_ON_LOAD_NEWS) }
        }
    }

    private fun configFabAddNews() {
        activityListNewsBinding.activityListaNoticiasFabSalvaNoticia.setOnClickListener {
            openFormInCreateMode()
        }
    }

    private fun configRecyclerView() {
        val divisor = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        activityListNewsBinding.activityListaNoticiasRecyclerview.addItemDecoration(divisor)
        activityListNewsBinding.activityListaNoticiasRecyclerview.adapter = adapter
        configAdapter()
    }

    private fun configAdapter() {
        adapter.whenItemClicked = this::openNewsViewer
    }

    private fun openFormInCreateMode() {
        val intent = Intent(this, FormNewsActivity::class.java)
        startActivity(intent)
    }

    private fun openNewsViewer(it: News) {
        val intent = Intent(this, NewsViewActivity::class.java)
        intent.putExtra(NEWS_KEY, it.id)
        startActivity(intent)
    }
}