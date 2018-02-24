package com.astimefades.beatsyncservice.model.db;

import com.astimefades.beatsyncservice.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository extends MongoRepository<Account> {

    @Autowired
    public AccountRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public Account findByEmail(String email) {
        Query query = Query.query(Criteria.where("email").is(email));

        return mongoTemplate.findOne(query, getDocumentClass());
    }

    @Override
    public String getCollection() {
        return "account";
    }

    @Override
    public Class<Account> getDocumentClass() {
        return Account.class;
    }
}
