package com.astimefades.beatsyncandroid.model.config

import android.app.Activity
import android.content.Context

/**
 * Created by tobycaulk on 3/3/18.
 */
object ApplicationConfiguration {
    val ACCOUNT_PREF_FILE = "com.astimefades.beatsyncandroid.account"
    val ACCOUNT_ID_PROP = "accountId"
    val ACCOUNT_PASSWORD_PROP = "accountPassword"
    val ACCOUNT_EMAIL_PROP = "accountEmail"

    fun getInstance(prefFile: String, activity: Activity) = activity.getSharedPreferences(prefFile, Context.MODE_PRIVATE)
}