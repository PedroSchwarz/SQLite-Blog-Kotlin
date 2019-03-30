package com.rodrigues.pedroschwarz.sqliteblog.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.rodrigues.pedroschwarz.sqliteblog.R
import com.rodrigues.pedroschwarz.sqliteblog.dao.UserDAO
import com.rodrigues.pedroschwarz.sqliteblog.fragment.PostsFragment
import com.rodrigues.pedroschwarz.sqliteblog.fragment.UsersFragment
import com.rodrigues.pedroschwarz.sqliteblog.model.User
import com.rodrigues.pedroschwarz.sqliteblog.utils.PreferencesUtil

class MainActivity : AppCompatActivity() {

    private lateinit var userDAO: UserDAO
    private lateinit var preferencesUtil: PreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userDAO = UserDAO(this)
        preferencesUtil = PreferencesUtil(this)

        setupActionBar()
        setFragment(PostsFragment())
        setupBotNav()

    }

    private fun setupActionBar() {
        val mainToolbar = findViewById<Toolbar>(R.id.main_toolbar)
        mainToolbar.title = "${getCurrentUser()!!.username}"
        setSupportActionBar(mainToolbar)
    }

    private fun setupBotNav() {
        val mainBotNav = findViewById<BottomNavigationView>(R.id.main_bot_nav)
        mainBotNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_user_posts -> {
                    setFragment(PostsFragment())
                    true
                }
                else -> {
                    setFragment(UsersFragment())
                    true
                }
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, fragment)
        transaction.commit()
    }

    private fun getCurrentUser(): User? {
        val currentUserId = preferencesUtil.getPrefs()
        return userDAO.getSingle(currentUserId.toInt())
    }

    private fun goToAuth() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun logoutUser() {
        preferencesUtil.clearPrefs()
        goToAuth()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_logout -> {
                logoutUser()
                return true
            }
            else -> {
                return true
            }
        }
    }
}
