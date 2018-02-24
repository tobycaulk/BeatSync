const mongojs = require('mongojs');
const config = require('../config');

function callbackHandler(resolve, reject, err, docs) {
    if(err) {
        reject(err);
    } else {
        resolve(docs);
    }
}

function getCollection(col) {
    let db = mongojs(config.db.name);
    return db.collection(col);
}

module.exports = function(col) {
    return {
        objectId: mongojs.ObjectId,
        save: (obj) => {
            return new Promise((resolve, reject) => {
                getCollection(col)
                    .save(obj, (err, doc) => {
                        callbackHandler(resolve, reject, err, doc);
                    });
            });
        },
        find: (query) => {
            return new Promise((resolve, reject) => {
                getCollection(col)
                    .find(query, (err, docs) => {
                        callbackHandler(resolve, reject, err, docs);
                    });
            });
        },
        findOne: (query) => {
            return new Promise((resolve, reject) => {
                getCollection(col)
                    .findOne(query, (err, doc) => {
                        callbackHandler(resolve, reject, err, doc);
                    });
            });
        },
        insert: (obj) => {
            return new Promise((resolve, reject) => {
                getCollection(col)
                    .insert(obj, (err, doc) => {
                        callbackHandler(resolve, reject, err, doc);
                    });
            });
        },
        update: (query, update) => {
            return new Promise((resolve, reject) => {
                getCollection(col)
                    .update(query, { $set: update }, { multi: true }, () => {
                        callbackHandler(resolve, reject, null, {});
                    });
            });
        },
        remove: (query) => {
            return new Promise((resolve, reject) => {
                getCollection(col)
                    .remove(query, (err, lastErrorObject) => {
                        callbackHandler(resolve, reject, err, {});
                    });
            });
        }
    };
};