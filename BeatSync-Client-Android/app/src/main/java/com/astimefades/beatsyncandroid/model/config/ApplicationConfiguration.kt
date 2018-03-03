package com.astimefades.beatsyncandroid.model.config

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

/**
 * Created by tobycaulk on 3/2/18.
 */
class ApplicationConfiguration {

    val ACCOUNT_PREF_FILE = "com.astimefades.beatsyncandroid.account"
    val ACCOUNT_ID_PROP = "accountId"

    fun getInstance(prefFile: String, activity: Activity) = activity.getSharedPreferences(prefFile, MODE_PRIVATE).edit()
}