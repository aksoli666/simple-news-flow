package com.example.simplenewsflow.design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenewsflow.R
import com.example.simplenewsflow.model.SourceByCategory

class NewsByCategoryAdapter : RecyclerView.Adapter<NewsByCategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val articleTitle: TextView = view.findViewById(R.id.article_title)
        val articleDate: TextView = view.findViewById(R.id.article_date)
    }

    private val callback = object : DiffUtil.ItemCallback<SourceByCategory>() {
        override fun areItemsTheSame(oldItem: SourceByCategory, newItem: SourceByCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SourceByCategory, newItem: SourceByCategory): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.articleTitle.text = article.description
        holder.articleDate.text = article.name

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { listener ->
                listener(article)
            }
        }
    }

    private var onItemClickListener: ((SourceByCategory) -> Unit)? = null
}