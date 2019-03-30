package com.rodrigues.pedroschwarz.sqliteblog.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rodrigues.pedroschwarz.sqliteblog.R
import com.rodrigues.pedroschwarz.sqliteblog.activity.UserActivity
import com.rodrigues.pedroschwarz.sqliteblog.model.User

class UsersAdapter(private val context: Context, private val users: List<User>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.single_user, p0, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.users.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val user = users[p1]
        p0.bindUsername(user.username)
        p0.bindEmail(user.email)
        p0.getCard().setOnClickListener {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra("userId", user.id)
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val singleUserUsername: TextView = itemView.findViewById(R.id.single_user_username)
        private val singleUserEmail: TextView = itemView.findViewById(R.id.single_user_email)
        private val singleUserCard: CardView = itemView.findViewById(R.id.single_user_card)

        fun bindUsername(username: String) {
            singleUserUsername.text = username
        }

        fun bindEmail(email: String) {
            singleUserEmail.text = email
        }

        fun getCard(): CardView {
            return singleUserCard
        }

    }
}