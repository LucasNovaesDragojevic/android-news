package com.news.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.news.R
import com.news.databinding.NewsItemBinding
import com.news.model.News

class ListNewsAdapter(
    private val context: Context,
    private val news: MutableList<News> = mutableListOf(),
    var whenItemClicked: (news: News) -> Unit = {}
) : RecyclerView.Adapter<ListNewsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var news: News
        init {
            itemView.setOnClickListener {
                if (::news.isInitialized) {
                    whenItemClicked(news)
                }
            }
        }

        fun bind(news: News) {
            this.news = news
            itemView.findViewById<TextView>(R.id.item_noticia_titulo).text = news.title
            itemView.findViewById<TextView>(R.id.item_noticia_texto).text = news.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false))
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
