package com.rodrigues.pedroschwarz.sqliteblog.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.rodrigues.pedroschwarz.sqliteblog.model.User
import com.rodrigues.pedroschwarz.sqliteblog.utils.DatabaseUtil
import java.lang.Exception

class UserDAO(context: Context) : IUserDAO {

    private var db: DatabaseUtil = DatabaseUtil(context, null)
    private var read: SQLiteDatabase
    private var write: SQLiteDatabase

    init {
        read = db.readableDatabase
        write = db.writableDatabase
    }

    override fun create(user: User): Long {
        val cv = ContentValues()
        cv.put("email", user.email)
        cv.put("username", user.username)
        cv.put("password", user.password)
        return try {
            write.insert(DatabaseUtil.USER_TABLE, null, cv)
        } catch (e: Exception) {
            -1L
        }
    }

    override fun getAll(): List<User>? {
        val users = arrayListOf<User>()
        val sql = "SELECT * FROM ${DatabaseUtil.USER_TABLE}"
        return try {
            val cursor = read.rawQuery(sql, null)
            while (cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex("id")
                val emailIndex = cursor.getColumnIndex("email")
                val usernameIndex = cursor.getColumnIndex("username")
                val passwordIndex = cursor.getColumnIndex("password")
                val user = User(
                    cursor.getLong(idIndex),
                    cursor.getString(emailIndex),
                    cursor.getString(usernameIndex),
                    cursor.getString(passwordIndex)
                )
                users.add(user)
            }
            users
        } catch (e: Exception) {
            null
        }
    }

    override fun getSingle(id: Int): User? {
        val sql = "SELECT * FROM ${DatabaseUtil.USER_TABLE} WHERE id = $id"
        return try {
            val cursor = read.rawQuery(sql, null)
            cursor.moveToFirst()
            val idIndex = cursor.getColumnIndex("id")
            val emailIndex = cursor.getColumnIndex("email")
            val usernameIndex = cursor.getColumnIndex("username")
            val passwordIndex = cursor.getColumnIndex("password")
            User(
                cursor.getLong(idIndex),
                cursor.getString(emailIndex),
                cursor.getString(usernameIndex),
                cursor.getString(passwordIndex)
            )
        } catch (e: Exception) {
            null
        }

    }

    override fun update(user: User): Boolean {
        val cv = ContentValues()
        cv.put("id", user.id)
        cv.put("email", user.email)
        cv.put("username", user.username)
        cv.put("password", user.password)
        return try {
            write.update(DatabaseUtil.USER_TABLE, cv, "id = ?", arrayOf(user.id.toString()))
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun delete(id: Int): Boolean {
        return try {
            write.delete(DatabaseUtil.USER_TABLE, "id = ?", arrayOf(id.toString()))
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun validateAuth(email: String, password: String, emailOnly: Boolean): Long {
        var sql = "SELECT * FROM ${DatabaseUtil.USER_TABLE} WHERE email = '$email'"
        if (!emailOnly) sql += " AND password = '$password'"
        var index = -1L
        return try {
            val cursor = read.rawQuery(sql, null)
            if (cursor.count > 0) {
                cursor.moveToFirst()
                index = cursor.getLong(cursor.getColumnIndex("id"))
            }
            index
        } catch (e: Exception) {
            -1L
        }
    }
}