package com.astimefades.beatsyncservice.service

import com.astimefades.beatsyncservice.model.Session
import com.astimefades.beatsyncservice.model.db.AccountRepository
import com.astimefades.beatsyncservice.model.error.BeatSyncError
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SessionService(@Autowired accountRepository: AccountRepository): BaseAccountService(accountRepository) {

    fun create(id: String, session: Session): Session {
        var account = getAccountByProxyId(id)
        if(account != null) {
            session.id = ObjectId().toString()
            account.session = session

            updateAccount(account)

            return session
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }
}