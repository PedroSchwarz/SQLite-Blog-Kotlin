package com.rodrigues.pedroschwarz.sqliteblog.utils

class TablesUtil {
    companion object {
        fun getTablesSql(): List<String> {
            val sqlList = ArrayList<String>()
            val userTableSql = "CREATE TABLE IF NOT EXISTS ${DatabaseUtil.USER_TABLE}(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "username varchar(50) NOT NULL, " +
                    "password varchar(50) NOT NULL" +
                    ")"
            val postTableSql = "CREATE TABLE IF NOT EXISTS ${DatabaseUtil.POST_TABLE}(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title VARCHAR(50) NOT NULL, " +
                    "description TEXT NOT NULL, " +
                    "userId INTEGER NOT NULL" +
                    ")"
            sqlList.add(userTableSql)
            sqlList.add(postTableSql)
            return sqlList
        }
    }
}