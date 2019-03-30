package com.rodrigues.pedroschwarz.sqliteblog.dao

import com.rodrigues.pedroschwarz.sqliteblog.model.User

interface IUserDAO {
    fun create(user: User): Long
    fun getAll(): List<User>?
    fun getSingle(id: Int): User?
    fun update(user: User): Boolean
    fun delete(id: Int): Boolean
    fun validateAuth(email: String, password: String, emailOnly: Boolean): Long
}