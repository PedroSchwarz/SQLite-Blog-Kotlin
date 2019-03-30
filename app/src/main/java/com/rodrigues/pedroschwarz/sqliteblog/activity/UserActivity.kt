package com.rodrigues.pedroschwarz.sqliteblog.activity

import android.content.Intent
import android.content.IntentSender
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.rodrigues.pedroschwarz.sqliteblog.R
import com.rodrigues.pedroschwarz.sqliteblog.adapter.PostsAdapter
import com.rodrigues.pedroschwarz.sqliteblog.dao.PostDAO
import com.rodrigues.pedroschwarz.sqliteblog.dao.UserDAO
import com.rodrigues.pedroschwarz.sqliteblog.model.Post
import com.rodrigues.pedroschwarz.sqliteblog.model.User

class UserActivity : AppCompatActivity() {

    private var userId: Int = -1
    private lateinit var user: User

    private lateinit var userDAO: UserDAO
    private lateinit var postDAO: PostDAO

    private lateinit var userPostsRv: RecyclerView
    private lateinit var posts: List<Post>
    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        userId = intent.extras!!.getLong("userId").toInt()

        userDAO = UserDAO(applicationContext)
        postDAO = PostDAO(applicationContext)

        getUserData()

        userPostsRv = findViewById(R.id.user_posts_rv)
        userPostsRv.setHasFixedSize(true)
        userPostsRv.layoutManager = LinearLayoutManager(applicationContext)

        setRecycler()

        findViewById<FloatingActionButton>(R.id.user_posts_mail_btn).setOnClickListener {
            sendEmail()
        }
    }

    private fun getUserData() {
        user = userDAO.getSingle(userId)!!
        findViewById<TextView>(R.id.user_data).text = "${user.email} \n${user.username}"
    }


    private fun getPosts() {
        posts = postDAO.getAll(true, userId)!!
    }

    private fun setRecycler() {
        getPosts()
        if (posts.isEmpty()) findViewById<TextView>(R.id.user_no_posts_txt).visibility = View.VISIBLE
        adapter = PostsAdapter(applicationContext, posts)
        userPostsRv.adapter = adapter
    }

    private fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(user.email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact from shopApp")
        emailIntent.type = "message/rfc822"

        startActivity(Intent.createChooser(emailIntent, "Choose an Email client:"))
    }
}
