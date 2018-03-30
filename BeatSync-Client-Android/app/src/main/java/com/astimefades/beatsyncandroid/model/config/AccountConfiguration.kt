package com.astimefades.beatsyncandroid.model.config

import android.app.Activity

/**
 * Created by tobycaulk on 3/6/18.
 */
class AccountConfiguration(activity: Activity) : ApplicationConfiguration(activity) {

    companion object {
        val ACCOUNT_PROXY_ID_PROP = "accountProxyId"
    }

    fun removeAccountInformation() {
        remove(ACCOUNT_PROXY_ID_PROP)
    }

    fun updateAccountInformation(proxyId: String) {
        writeString(ACCOUNT_PROXY_ID_PROP, proxyId)
    }

    fun getProxyId() = getString(ACCOUNT_PROXY_ID_PROP)

    override fun getFile(): String {
        return "com.astimefades.beatsyncandroid.account"
    }
}