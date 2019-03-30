package com.rodrigues.pedroschwarz.sqliteblog.fragment


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.rodrigues.pedroschwarz.sqliteblog.R
import com.rodrigues.pedroschwarz.sqliteblog.activity.NewPostActivity
import com.rodrigues.pedroschwarz.sqliteblog.adapter.PostsAdapter
import com.rodrigues.pedroschwarz.sqliteblog.dao.PostDAO
import com.rodrigues.pedroschwarz.sqliteblog.model.Post
import com.rodrigues.pedroschwarz.sqliteblog.utils.PreferencesUtil

class PostsFragment : Fragment() {

    private lateinit var userPostsRv: RecyclerView
    private lateinit var posts: List<Post>
    private lateinit var adapter: PostsAdapter

    private lateinit var postDAO: PostDAO
    private lateinit var preferences: PreferencesUtil
    private var currentUserId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postDAO = PostDAO(activity!!.applicationContext)
        preferences = PreferencesUtil(activity!!.applicationContext)
        currentUserId = preferences.getPrefs().toInt()

        userPostsRv = view.findViewById(R.id.posts_rv)
        userPostsRv.setHasFixedSize(true)
        userPostsRv.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        view.findViewById<FloatingActionButton>(R.id.posts_new_btn).setOnClickListener {
            goToNewPost()
        }
    }

    private fun getPosts() {
        posts = postDAO.getAll(false, currentUserId)!!
    }

    private fun setRecycler() {
        getPosts()
        if (posts.isEmpty()) activity!!.findViewById<TextView>(R.id.no_posts_txt).visibility = View.VISIBLE
        adapter = PostsAdapter(activity!!.applicationContext, posts)
        userPostsRv.adapter = adapter
    }

    private fun goToNewPost() {
        startActivity(Intent(activity!!.applicationContext, NewPostActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        setRecycler()
    }
}
