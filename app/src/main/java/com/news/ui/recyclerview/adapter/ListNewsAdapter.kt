package com.news.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.news.databinding.NewsItemBinding
import com.news.model.News

class ListNewsAdapter(
    private val context: Context,
    private val news: MutableList<News> = mutableListOf(),
    var whenItemClicked: (news: News) -> Unit = {}
) : RecyclerView.Adapter<ListNewsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var news: News
        private val itemNewsTitle : TextView
        private val itemNewsText : TextView
        init {
            itemNewsTitle = binding.itemNewsTitle
            itemNewsText = binding.itemNewsText
            binding.root.setOnClickListener {
                if (::news.isInitialized)
                    whenItemClicked(news)
            }
        }

        fun bind(news: News) {
            this.news = news
            itemNewsTitle.text = news.title
            itemNewsText.text = news.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val newsItemBinding = NewsItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(newsItemBinding)
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = news[position]
        holder.bind(news)
    }

    fun update(news: List<News>) {
        notifyItemRangeRemoved(0, this.news.size)
        this.news.clear()
        this.news.addAll(news)
        notifyItemRangeInserted(0, this.news.size)
    }
}
