package com.astimefades.beatsyncservice.model.db;

import com.astimefades.beatsyncservice.model.Model;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

public abstract class MongoRepository<T extends Model> {

    private static final String ID_FIELD = "_id";

    protected MongoTemplate mongoTemplate;
    private String collection;
    private Class<T> documentClass;

    public MongoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public abstract String getCollection();
    public abstract Class<T> getDocumentClass();

    public T create(T t) {
        mongoTemplate.insert(t, getCollection());

        return findOne(t.getId());
    }

    public T findOne(String id) {
        return mongoTemplate.findOne(getQueryWhereIdIs(id), getDocumentClass(), getCollection());
    }

    public T update(T t) {
        mongoTemplate.save(t, getCollection());

        return findOne(t.getId());
    }

    public boolean delete(String id) {
        mongoTemplate.remove(getQueryWhereIdIs(id), getCollection());

        return findOne(id) == null;
    }

    public Query getQueryWhereIdIs(String id) {
        return Query.query(Criteria.where(ID_FIELD).is(id));
    }
}