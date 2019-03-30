package com.rodrigues.pedroschwarz.sqliteblog.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtil(private val context: Context) {
    private var preferences: SharedPreferences? = null
    private val SHARED_PREF_FILE_NAME = "SHOP_APP_PREFS"

    private fun initPrefs() {
        if (preferences == null) {
            preferences = context.getSharedPreferences(SHARED_PREF_FILE_NAME, 0)
        }
    }

    fun savePrefs(id: Long) {
        initPrefs()
        val editor = this.preferences!!.edit()
        editor.putLong("id", id)
        editor.apply()
    }

    fun getPrefs(): Long {
        initPrefs()
        return this.preferences!!.getLong("id", -1L)
    }

    fun clearPrefs() {
        initPrefs()
        val editor = this.preferences!!.edit()
        editor.remove("id")
        editor.apply()
    }
}