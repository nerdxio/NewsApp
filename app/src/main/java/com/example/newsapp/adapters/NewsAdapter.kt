package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticaleViewHolder>() {

    inner class ArticaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(article: Article?) {
            article?.let {
                itemView.apply {
                    Glide.with(this).load(article.urlToImage).into(ivArticleImage)
                    tvSource.text = article.source.name
                    tvTitle.text = article.title
                    tvDescription.text = article.description
                    tvPublishedAt.text = article.publishedAt
                    setOnClickListener {
                        onItemClickListener?.also {
                            it(article)
                        }
                    }
                }
            }

        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticaleViewHolder {
        return ArticaleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article_preview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: ArticaleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bindData(article)
//        holder.itemView.apply {
//            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
//            tvSource.text = article.source.name
//            tvTitle.text =article.title
//            tvDescription.text=article.description
//            tvPublishedAt.text=article.publishedAt
//             setOnClickListener {
//                onItemClickListener?.also {
//                    it(article) }
//            }
//        }

    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}