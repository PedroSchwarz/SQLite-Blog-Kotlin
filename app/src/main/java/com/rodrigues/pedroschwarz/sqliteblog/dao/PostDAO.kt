package com.rodrigues.pedroschwarz.sqliteblog.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.rodrigues.pedroschwarz.sqliteblog.model.Post
import com.rodrigues.pedroschwarz.sqliteblog.utils.DatabaseUtil
import java.lang.Exception

class PostDAO(context: Context) : IPostDAO {

    private val db = DatabaseUtil(context, null)
    private var read: SQLiteDatabase
    private var write: SQLiteDatabase

    init {
        read = db.readableDatabase
        write = db.writableDatabase
    }

    override fun create(post: Post): Long {
        val cv = ContentValues()
        cv.put("title", post.title)
        cv.put("description", post.description)
        cv.put("userId", post.userId)
        return try {
            write.insert(DatabaseUtil.POST_TABLE, null, cv)
        } catch (e: Exception) {
            -1L
        }
    }

    override fun getAll(currentUserOnly: Boolean, userId: Int?): List<Post>? {
        val posts = arrayListOf<Post>()
        var sql = "SELECT * FROM ${DatabaseUtil.POST_TABLE}"
        if (currentUserOnly) sql += " WHERE userId = $userId"
        return try {
            val cursor = read.rawQuery(sql, null)
            while (cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex("id")
                val titleIndex = cursor.getColumnIndex("title")
                val descIndex = cursor.getColumnIndex("description")
                val userIdIndex = cursor.getColumnIndex("userId")
                val post = Post(
                    cursor.getLong(idIndex),
                    cursor.getString(titleIndex),
                    cursor.getString(descIndex),
                    cursor.getInt(userIdIndex)
                )
                posts.add(post)
            }
            posts
        } catch (e: Exception) {
            null
        }
    }

    override fun getSingle(id: Int): Post? {
        var sql = "SELECT * FROM ${DatabaseUtil.POST_TABLE} WHERE id = $id"
        return try {
            val cursor = read.rawQuery(sql, null)
            cursor.moveToFirst()
            val idIndex = cursor.getColumnIndex("id")
            val titleIndex = cursor.getColumnIndex("title")
            val descIndex = cursor.getColumnIndex("description")
            val userIdIndex = cursor.getColumnIndex("userId")
            Post(
                cursor.getLong(idIndex),
                cursor.getString(titleIndex),
                cursor.getString(descIndex),
                cursor.getInt(userIdIndex)
            )
        } catch (e: Exception) {
            null
        }
    }

    override fun update(post: Post): Boolean {
        val cv = ContentValues()
        cv.put("id", post.id)
        cv.put("title", post.title)
        cv.put("description", post.description)
        cv.put("userId", post.userId)
        return try {
            write.update(DatabaseUtil.POST_TABLE, cv, "id = ?", arrayOf(post.id.toString()))
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun delete(id: Int): Boolean {
        return try {
            write.delete(DatabaseUtil.POST_TABLE, "id = ?", arrayOf(id.toString()))
            true
        } catch (e: Exception) {
            false
        }
    }
}