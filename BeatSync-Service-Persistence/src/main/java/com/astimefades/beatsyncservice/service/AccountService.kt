package com.astimefades.beatsyncservice.service

import com.astimefades.beatsyncservice.model.Account
import com.astimefades.beatsyncservice.model.db.AccountRepository
import com.astimefades.beatsyncservice.model.error.BeatSyncError
import com.astimefades.beatsyncservice.model.request.CreateAccountRequest
import com.astimefades.beatsyncservice.model.request.LoginAccountRequest
import com.astimefades.beatsyncservice.util.PasswordUtil
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountService(@Autowired accountRepository: AccountRepository): BaseAccountService(accountRepository) {

    fun checkLogin(proxyId: String) = getAccountByProxyId(proxyId) != null

    fun login(request: LoginAccountRequest): String {
        var account = getAccountByEmail(request.email)
        if(account != null) {
            if(PasswordUtil.checkPassword(request.password, account.password)) {
                var proxyId = account.proxyId
                if(proxyId == null || proxyId == "") {
                    proxyId = ObjectId().toString()
                    account.proxyId = proxyId
                    updateAccount(account)
                }

                return proxyId
            } else {
                throw BeatSyncError.getException(BeatSyncError.INVALID_PASSWORD_FOR_ACCOUNT)
            }
        } else {
            throw BeatSyncError.getException(BeatSyncError.EMAIL_NOT_FOUND)
        }
    }

    fun create(request: CreateAccountRequest): String {
        if(isEmailTaken(request.email)) {
            throw BeatSyncError.getException(BeatSyncError.EMAIL_TAKEN)
        }

        var proxyId = ObjectId().toString()

        var account = Account()
        account.email = request.email
        account.password = PasswordUtil.getHash(request.password)
        account.proxyId = proxyId

        accountRepository.create(account)

        return proxyId
    }

    fun logout(proxyId: String): Boolean {
        var account = getAccountByProxyId(proxyId)
        if(account != null) {
            account.proxyId = null
            updateAccount(account)

            return getAccountByProxyId(proxyId) == null
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }
}