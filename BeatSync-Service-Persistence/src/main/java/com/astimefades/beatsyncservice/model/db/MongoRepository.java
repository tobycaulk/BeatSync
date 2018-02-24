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
        t.getDetails().setDatesToNow();
        mongoTemplate.insert(t, getCollection());

        return findOne(t.getId());
    }

    public T findOne(ObjectId id) {
        return mongoTemplate.findOne(getQueryWhereIdIs(id), getDocumentClass(), getCollection());
    }

    public T update(T t) {
        t.getDetails().setDatesToNow();
        mongoTemplate.save(t, getCollection());

        return findOne(t.getId());
    }

    public boolean delete(ObjectId id) {
        mongoTemplate.remove(getQueryWhereIdIs(id), getCollection());

        return findOne(id) == null;
    }

    public Query getQueryWhereIdIs(ObjectId id) {
        return Query.query(Criteria.where(ID_FIELD).is(id));
    }
}