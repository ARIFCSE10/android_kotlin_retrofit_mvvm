package com.beliefit.tmq.mybookshop.util

import android.content.Context
import android.content.SharedPreferences
import com.beliefit.tmq.mybookshop.R

object SharedPrefUtil {

    fun getSharedPreferences(context: Context?): SharedPreferences? {
        var sharedPref =
            context?.getSharedPreferences(context.getString(R.string.sp_app_name), Context.MODE_PRIVATE) ?: return null
        return sharedPref
    }

    fun setUserHasLoggedInPref(data: Boolean, context: Context?) {
        val sharedPref = getSharedPreferences(context) ?: return
        with(sharedPref.edit()) {
            putBoolean(context?.getString(R.string.user_logged_in_pref), data)
            commit()
        }
    }

    fun getUserHasLoggedInPref(context: Context?): Boolean {
        val sharedPref = getSharedPreferences(context) ?: return false
        return sharedPref.getBoolean(context?.getString(R.string.user_logged_in_pref), false)
    }

    fun setUserTokenPref(data: String?, context: Context?) {
        val sharedPref = getSharedPreferences(context) ?: return
        with(sharedPref.edit()) {
            putString(context?.getString(R.string.user_token_pref), data)
            commit()
        }
    }

    fun getUserTokenPref(context: Context?): String {
        val sharedPref = getSharedPreferences(context) ?: return ""
        return sharedPref.getString(context?.getString(R.string.user_token_pref), "")
    }

    fun setUserNamePref(data: String?, context: Context?) {
        val sharedPref = getSharedPreferences(context) ?: return
        with(sharedPref.edit()) {
            putString(context?.getString(R.string.user_name_pref), data)
            commit()
        }
    }

    fun getUserNamePref(context: Context?): String {
        val sharedPref = getSharedPreferences(context) ?: return ""
        return sharedPref.getString(context?.getString(R.string.user_name_pref), "")
    }

    fun setUserEmailPref(data: String?, context: Context?) {
        val sharedPref = getSharedPreferences(context) ?: return
        with(sharedPref.edit()) {
            putString(context?.getString(R.string.user_email_pref), data)
            commit()
        }
    }

    fun getUserEmailPref(context: Context?): String {
        val sharedPref = getSharedPreferences(context) ?: return ""
        return sharedPref.getString(context?.getString(R.string.user_email_pref), "")
    }
}