package com.rodrigues.pedroschwarz.sqliteblog.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rodrigues.pedroschwarz.sqliteblog.R
import com.rodrigues.pedroschwarz.sqliteblog.adapter.UsersAdapter
import com.rodrigues.pedroschwarz.sqliteblog.dao.UserDAO
import com.rodrigues.pedroschwarz.sqliteblog.model.User

class UsersFragment : Fragment() {

    private lateinit var usersRv: RecyclerView
    private lateinit var users: List<User>
    private lateinit var adapter: UsersAdapter

    private lateinit var userDAO: UserDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDAO = UserDAO(activity!!.applicationContext)

        usersRv = view.findViewById(R.id.users_rv)
        usersRv.setHasFixedSize(true)
        usersRv.layoutManager = LinearLayoutManager(activity!!.applicationContext)
    }

    private fun getUsers() {
        users = userDAO.getAll()!!
    }

    private fun setRecycler() {
        getUsers()
        adapter = UsersAdapter(activity!!, users)
        usersRv.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        setRecycler()
    }
}
