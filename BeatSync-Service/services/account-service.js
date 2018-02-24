const config = require('../config');
const mongoProvider = require('../db/mongo-provider')(config.db.collections.account);
const errorCodes = require('../error-codes');
const passwordProvider = require('../password-provider');
const util = require('../util');

const sessionState = {
    WAITING: "WAITING",
    MAP: "MAP",
    VALIDATE: "VALIDATE",
    START: "START"
};

function scrubAccount(account) {
    delete account.password;
    return account;
}

function hashPassword(password) {
    return passwordProvider.hash(password);
}

function checkIfEmailIsTaken(email) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .find({ email: email })
            .then(accounts => resolve(accounts.length > 0))
            .catch(err => reject(err));
    });
}

function insertAccount(email, password) {
    return new Promise((resolve, reject) => {
        hashPassword(password)
            .then(hashedPassword => {
                let account = {
                    email: email,
                    password: hashedPassword,
                    tracks: {},
                    playlists: {},
                    session: {}
                };
                util.attachTimestamps(account);

                mongoProvider
                    .insert(account)
                    .then(account => resolve(scrubAccount(account)))
                    .catch(err => {
                        console.log("Error while inserting user into DB [" + err + "]");
                        reject(errorCodes.generic);
                    });
            })
            .catch(err => {
                console.log("Error while hashing password [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function create(email, password) {
    return new Promise((resolve, reject) => {
        checkIfEmailIsTaken(email)
            .then(isTaken => {
                if(isTaken) {
                    reject(errorCodes.account.email.taken);
                } else {
                    insertAccount(email, password)
                        .then(account => resolve(account))
                        .catch(errorCode => reject(errorCode));
                }
            })
            .catch(err => {
                console.log("Error while creating account [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function get(id) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(id) })
            .then(account => {
                if(account === undefined) {
                    reject(errorCodes.account.notFound);
                } else {
                    resolve(scrubAccount(account));
                }
            })
            .catch(err => {
                console.log("Error while getting account with id[" + id + "]. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function remove(id) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .remove({ _id: mongoProvider.objectId(id) })
            .then(() => resolve())
            .catch(err => {
                console.log("Error while removing account with id[" + id + "]. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function addTrack(id, track) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(id) })
            .then(account => {
                if(account === undefined) {
                    reject(errorCodes.account.notFound);
                } else {
                    track._id = mongoProvider.objectId();
                    util.attachTimestamps(track);
                    account.tracks[track._id] = track;

                    update(account)
                        .then(success => resolve(scrubAccount(account)))
                        .catch(err => reject(err));
                }
            })
            .catch(err => {
                console.log("Error while rerieving account with id[" + id + "] to add track. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function removeTrack(accountId, trackId) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(accountId) })
            .then(account => {
                if(account === undefined) {
                    reject(errorCodes.account.notFound);
                } else {
                    delete account.tracks[trackId];
                    update(account)
                        .then(success => resolve(scrubAccount(account)))
                        .catch(err => reject(err));
                }
            })
            .catch(err => {
                console.log("Error while retrieving account with id[" + accountId + "] to remove track with id[" + trackId + "]. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function updateTrack(id, track) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(id) })
            .then(account => {
                if(account.tracks[track._id] === undefined) {
                    reject(errorCodes.account.track.notFound);
                } else {
                    util.attachTimestamps(track);
                    account.tracks[track._id] = track;

                    update(account)
                        .then(success => resolve(scrubAccount(account)))
                        .catch(err => reject(err));
                }
            })
            .catch(err => {
                console.log("Error while retrieving account with id[" + id + "] to update track with id[" + track.id + "]. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function createPlaylist(id, playlist) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(id) })
            .then(account => {
                if(account === undefined) {
                    reject(errorCodes.account.notFound);
                } else {
                    util.attachTimestamps(playlist);
                    playlist._id = mongoProvider.objectId();
                    account.playlists[playlist._id] = playlist;

                    update(account)
                        .then(success => resolve(scrubAccount(account)))
                        .catch(err => reject(err));
                }
            })
            .catch(err => {
                console.log("Error while retrieving account with id[" + id + "] to create playlist. Error [" + err + "]");
                reject(errorCode.generic);
            });
    });
}

function updatePlaylist(id, playlist) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(id) })
            .then(account => {
                if(account === undefined) {
                    reject(errorCodes.account.notFound);
                } else {
                    util.attachTimestamps(playlist);
                    account.playlists[playlist._id] = playlist;

                    update(account)
                        .then(success => resolve(scrubAccount(account)))
                        .catch(err => reject(err));
                }
            })
            .catch(err => {
                console.log("Error while retrieving account with id[" + id + "] to update playlist with id[" + playlist._id + "]. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function removePlaylist(accountId, playlistId) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(accountId) })
            .then(account => {
                if(account === undefined) {
                    reject(errorCodes.account.notFound);
                } else {
                    delete account.playlists[playlistId];
                    
                    update(account)
                        .then(success => resolve(scrubAccount(account)))
                        .catch(err => reject(err));
                }
            })
            .catch(err => {
                console.log("Error while retrieving account with id[" + accountId + "] to remove playlist with id[" + playlistId + "]. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function createSession(id, session) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(id) })
            .then(account => {
                if(account === undefined) {
                    reject(errorCodes.account.notFound);
                } else {
                    account.session = {};

                    util.attachTimestamps(session);
                    session._id = mongoProvider.objectId();
                    session.pin = util.getRandomPin();
                    session.state = sessionState.WAITING;
                    session.clients = {};
                    account.session = session;

                    update(account)
                        .then(success => resolve(scrubAccount(account)))
                        .catch(err => reject(err));
                }
            })
            .catch(err => {
                console.log("Error while retrieving account with id[" + id + "] to create session. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function getSessionState(id) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(id) })
            .then(account => {
                if(account === undefined) {
                    reject(errorCodes.account.notFound);
                } else {
                    resolve(account.session.state);
                }
            })
            .catch(err => {
                console.log("Error while retrieving account with id[" + id + "] while checking session status. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function startSession(id) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(id) })
            .then(account => {
                if(account === undefined) {
                    reject(errorCodes.account.notFound);
                } else {
                    if(account.session.state != sessionState.WAITING) {
                        reject(errorCodes.account.session.alreadyStarted);
                    } else {
                        util.attachTimestamps(account.session);
                        account.session.state = sessionState.MAP;
                        let client = createSessionCient();
                        account.session.clients[client._id] = client;
                        
                        update(account)
                            .then(success => resolve({ id: client._id }))
                            .catch(err => reject(err));
                    }
                }
            })
            .catch(err => {
                console.log("Error while retrieving account with id[" + id + "] to start session. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function joinSession(id) {
    return new Promise((resolve, reject) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(id) })
            .then(account => {
                if(account === undefined) {
                    reject(errorCodes.account.notFound);
                } else {
                    let client = createSessionClient();
                    account.session.clients[client._id] = client;
                }
            })
            .catch(err => {
                console.log("Error while retrieving account with id[" + id + "] to process join session request. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

function createSessionClient() {
    let client = {
        _id: mongoProvider.objectId()
    }
    util.attachTimestamps(client);
    //attach last "heard from" timestamp here

    return client;
}

function update(account) {
    return new Promise((resolve, reject) => {
        util.attachTimestamps(account);

        mongoProvider
            .update({ _id: mongoProvider.objectId(account._id) }, account)
            .then(success => {
                resolve(success);
            })
            .catch(err => {
                console.log("Error while updating account with id[" + account._id + "]. Error [" + err + "]");
                reject(errorCodes.generic);
            });
    });
}

module.exports = {
    create: create,
    get: get,
    remove: remove,
    addTrack: addTrack,
    updateTrack: updateTrack,
    removeTrack: removeTrack,
    createPlaylist: createPlaylist,
    updatePlaylist: updatePlaylist,
    removePlaylist: removePlaylist,
    createSession: createSession,
    getSessionState: getSessionState,
    startSession: startSession
};