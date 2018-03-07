package com.astimefades.beatsyncservice.service

import com.astimefades.beatsyncservice.model.Account
import com.astimefades.beatsyncservice.model.db.AccountRepository
import org.springframework.beans.factory.annotation.Autowired

open class BaseAccountService(@Autowired val accountRepository: AccountRepository) {

    fun getAccountByProxyId(proxyId: String) = accountRepository.findByProxyId(proxyId)

    fun getAccountByEmail(email: String) = accountRepository.findByEmail(email)

    fun getAccount(id: String) = accountRepository.findOne(id)

    fun removeAccount(id: String) = accountRepository.delete(id)

    fun updateAccount(account: Account) = accountRepository.update(account)

    fun isEmailTaken(email: String) = accountRepository.findByEmail(email) != null
}