package com.rodrigues.pedroschwarz.sqliteblog.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.rodrigues.pedroschwarz.sqliteblog.R
import com.rodrigues.pedroschwarz.sqliteblog.dao.UserDAO
import com.rodrigues.pedroschwarz.sqliteblog.model.Auth
import com.rodrigues.pedroschwarz.sqliteblog.model.User
import com.rodrigues.pedroschwarz.sqliteblog.utils.PreferencesUtil

class AuthActivity : AppCompatActivity() {

    private lateinit var emailField: EditText
    private lateinit var usernameField: EditText
    private lateinit var passField: EditText

    private lateinit var authBtn: Button
    private lateinit var switchBtn: Button

    private lateinit var userDAO: UserDAO

    private lateinit var preferencesUtil: PreferencesUtil

    private var authMode: Auth = Auth.Login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        userDAO = UserDAO(this)
        preferencesUtil = PreferencesUtil(this)

        checkAuth()

        emailField = findViewById(R.id.email)
        usernameField = findViewById(R.id.username)
        passField = findViewById(R.id.pass)
        authBtn = findViewById(R.id.auth_btn)
        switchBtn = findViewById(R.id.switch_btn)

        switchBtn.setOnClickListener {
            switchMode()
        }

        setLoginBtn()
    }

    private fun checkAuth() {
        if (preferencesUtil.getPrefs() != -1L) {
            goToMain()
        }
    }

    private fun switchMode() {
        when (authMode) {
            Auth.Login -> {
                authMode = Auth.Register
                usernameField.visibility = View.VISIBLE
                usernameField.animate().alpha(1f).duration = 1000
                authBtn.text = "Register"
                switchBtn.text = "Switch to Login"
                setRegisterBtn()
            }
            else -> {
                authMode = Auth.Login
                usernameField.visibility = View.GONE
                usernameField.animate().alpha(0f).duration = 1000
                authBtn.text = "Login"
                switchBtn.text = "Switch to Register"
                setLoginBtn()
            }
        }
    }

    private fun setRegisterBtn() {
        var email: String
        var username: String
        var password: String

        authBtn.setOnClickListener {
            email = emailField.text.toString()
            username = usernameField.text.toString()
            password = passField.text.toString()
            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                val validateUserId = userDAO.validateAuth(email, password, true)
                if (validateUserId != -1L) {
                    Snackbar.make(
                        findViewById(R.id.auth_layout),
                        "Email already in use, try another one!",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    val userId = userDAO.create(User(null, email, username, password))
                    if (userId != -1L) {
                        preferencesUtil.savePrefs(userId)
                        goToMain()
                    } else {
                        Snackbar.make(
                            findViewById(R.id.auth_layout),
                            "Something went wrong during registration!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Snackbar.make(findViewById(R.id.auth_layout), "Fill all the fields!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setLoginBtn() {
        var email: String
        var password: String

        authBtn.setOnClickListener {
            email = emailField.text.toString()
            password = passField.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val userId = userDAO.validateAuth(email, password, false)
                if (userId != -1L) {
                    preferencesUtil.savePrefs(userId)
                    goToMain()
                } else {
                    Snackbar.make(
                        findViewById(R.id.auth_layout),
                        "User not found!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            } else {
                Snackbar.make(findViewById(R.id.auth_layout), "Fill all the fields!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
