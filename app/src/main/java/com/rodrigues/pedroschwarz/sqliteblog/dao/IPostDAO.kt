package com.rodrigues.pedroschwarz.sqliteblog.dao

import com.rodrigues.pedroschwarz.sqliteblog.model.Post

interface IPostDAO {
    fun create(post: Post): Long
    fun getAll(currentUserOnly: Boolean, userId: Int? = null): List<Post>?
    fun getSingle(id: Int): Post?
    fun update(post: Post): Boolean
    fun delete(id: Int): Boolean
}