const mongojs = require('mongojs');

function callbackHandler(resolve, reject, err, docs) {
    if(err) {
        reject(err);
    } else {
        resolve(docs);
    }
}

function getCollection(col, db) {
    let mongoDb = mongojs(db);
    return mongoDb.collection(col);
}

module.exports = function(col, db) {
    return {
        objectId: mongojs.ObjectId,
        save: (obj, db) => {
            return new Promise((resolve, reject) => {
                getCollection(col, db)
                    .save(obj, (err, doc) => {
                        callbackHandler(resolve, reject, err, doc);
                    });
            });
        },
        find: (query) => {
            return new Promise((resolve, reject) => {
                getCollection(col, db)
                    .find(query, (err, docs) => {
                        callbackHandler(resolve, reject, err, docs);
                    });
            });
        },
        findOne: (query) => {
            return new Promise((resolve, reject) => {
                getCollection(col, db)
                    .findOne(query, (err, doc) => {
                        callbackHandler(resolve, reject, err, doc);
                    });
            });
        },
        insert: (obj) => {
            return new Promise((resolve, reject) => {
                getCollection(col, db)
                    .insert(obj, (err, doc) => {
                        callbackHandler(resolve, reject, err, doc);
                    });
            });
        },
        update: (query, update) => {
            return new Promise((resolve, reject) => {
                getCollection(col, db)
                    .update(query, { $set: update }, { multi: true }, () => {
                        callbackHandler(resolve, reject, null, {});
                    });
            });
        },
        remove: (query) => {
            return new Promise((resolve, reject) => {
                getCollection(col, db)
                    .remove(query, (err, lastErrorObject) => {
                        callbackHandler(resolve, reject, err, {});
                    });
            });
        }
    };
};