package com.rodrigues.pedroschwarz.sqliteblog.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Button
import android.widget.EditText
import com.rodrigues.pedroschwarz.sqliteblog.R
import com.rodrigues.pedroschwarz.sqliteblog.dao.PostDAO
import com.rodrigues.pedroschwarz.sqliteblog.model.Post
import com.rodrigues.pedroschwarz.sqliteblog.utils.PreferencesUtil

class NewPostActivity : AppCompatActivity() {

    private lateinit var newPostTitle: EditText
    private lateinit var newPostDesc: EditText

    private lateinit var preferences: PreferencesUtil
    private var currentUserId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        preferences = PreferencesUtil(this)
        currentUserId = preferences.getPrefs().toInt()

        newPostTitle = findViewById(R.id.new_post_title)
        newPostDesc = findViewById(R.id.new_post_desc)

        findViewById<Button>(R.id.new_post_btn).setOnClickListener {
            if (newPostTitle.text.isNotEmpty() && newPostDesc.text.isNotEmpty()) {
                val post = Post(
                    null,
                    newPostTitle.text.toString(),
                    newPostDesc.text.toString(),
                    currentUserId
                )
                if (PostDAO(this).create(post) != -1L) {
                    finish()
                } else {
                    Snackbar.make(
                        findViewById(R.id.new_post_layout),
                        "Something went wrong when creating post!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            } else {
                Snackbar.make(findViewById(R.id.new_post_layout), "Fill all the fields!", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
