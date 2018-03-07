package com.astimefades.beatsyncandroid.model.config

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KFunction3

/**
 * Created by tobycaulk on 3/6/18.
 */
abstract open class ApplicationConfiguration(activity: Activity) {

    val sharedPreferences = activity.getSharedPreferences(getFile(), Context.MODE_PRIVATE)

    abstract fun getFile(): String

    private fun getSharedPreferencesEditorInstance() = sharedPreferences.edit()

    fun writeString(prop: String, value: String) {
        val editor = getSharedPreferencesEditorInstance()
        editor.putString(prop, value)
        editor.apply()
    }

    fun getString(prop: String) = sharedPreferences.getString(prop, null)
}