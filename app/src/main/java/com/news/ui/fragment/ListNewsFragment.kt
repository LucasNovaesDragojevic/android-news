package com.news.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.news.R
import com.news.model.News
import com.news.ui.activity.FAIL_ON_LOAD_NEWS
import com.news.ui.fragment.extensions.showError
import com.news.ui.recyclerview.adapter.ListNewsAdapter
import com.news.ui.viewmodel.ListNewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListNewsFragment : Fragment() {

    private val adapter by lazy {
        context?.let {
            ListNewsAdapter(it)
        } ?: throw IllegalArgumentException("Invalid context")
    }

    private val viewModel: ListNewsViewModel by viewModel()

    var whenFabSaveNewsClicked: () -> Unit = {}

    var whenNewsSelect: (news: News) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNews()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configRecyclerView()
        configFabAddNews()
    }

    private fun findNews() {
        viewModel.findAll().observe(this) { resource ->
            resource.data?.let { adapter.update(it) }
            resource.error?.let { showError(FAIL_ON_LOAD_NEWS) }
        }
    }

    private fun configFabAddNews()
    {
        view?.findViewById<FloatingActionButton>(R.id.lista_noticias_fab_salva_noticia)
            ?.setOnClickListener {
                Log.i("DEV-INTERNAL" , "Cliquei no FAB")
                whenFabSaveNewsClicked()
            }
    }

    private fun configRecyclerView() {
        val divisor = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.lista_noticias_recyclerview)
        recyclerView?.addItemDecoration(divisor)
        recyclerView?.adapter = adapter
        configAdapter()
    }

    private fun configAdapter() {
        adapter.whenItemClicked = whenNewsSelect
    }
}