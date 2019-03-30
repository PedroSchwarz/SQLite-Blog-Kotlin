package com.rodrigues.pedroschwarz.sqliteblog.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class DatabaseUtil(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlList = TablesUtil.getTablesSql()
        sqlList.forEach {
            try {
                db!!.execSQL(it)
                Log.i("DATABASE", "Success creating $it table!")
            } catch (e: Exception) {
                Log.i("DATABASE", "Error creating $it table!")
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val userDropSql = "DROP TABLE IF EXISTS $USER_TABLE"
        val postDropSql = "DROP TABLE IF EXISTS $POST_TABLE"
        try {
            db!!.execSQL(userDropSql)
            db.execSQL(postDropSql)
            onCreate(db)
            Log.i("DATABASE", "Upgrade success!")
        } catch (e: Exception) {
            Log.i("DATABASE", "Upgrade error!")
        }
    }

    companion object {
        const val DATABASE_NAME = "blog_app.db"
        const val DATABASE_VERSION = 1

        const val USER_TABLE = "users"
        const val POST_TABLE = "posts"
    }
}