package com.rodrigues.pedroschwarz.sqliteblog.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rodrigues.pedroschwarz.sqliteblog.R
import com.rodrigues.pedroschwarz.sqliteblog.model.Post

class PostsAdapter(private val context: Context, private val posts: List<Post>) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.single_post, p0, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.posts.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val post = posts[p1]
        p0.bindTitle(post.title)
        p0.bindDesc(post.description)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val singlePostTitle: TextView = itemView.findViewById(R.id.single_post_title)
        private val singlePostDesc: TextView = itemView.findViewById(R.id.single_post_desc)

        fun bindTitle(title: String) {
            singlePostTitle.text = title
        }

        fun bindDesc(desc: String) {
            singlePostDesc.text = desc
        }

    }
}