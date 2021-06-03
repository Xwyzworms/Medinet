package com.reyhanabbywahyu.medinet2.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.reyhanabbywahyu.medinet2.BerandaActivity
import com.reyhanabbywahyu.medinet2.`class`.UserResponse

var user : UserResponse = UserResponse()

object PreferenceUtils {

    fun saveEmail(email : String, context: Context) : Boolean {
        val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor : SharedPreferences.Editor = prefs.edit()
        prefsEditor.putString(Constants.KEY_EMAIL,email)
        prefsEditor.apply()
        return true
    }

    fun savePassword(pass : String, context: Context) : Boolean {
        val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor : SharedPreferences.Editor = prefs.edit()
        prefsEditor.putString(Constants.KEY_PASSWORD, pass)
        prefsEditor.apply()
        return true
    }

    fun getEmail(context: Context) : String? {
        val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(Constants.KEY_EMAIL,null)
    }

    fun getPassword(context: Context) : String? {
        val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(Constants.KEY_PASSWORD,null)
    }


}