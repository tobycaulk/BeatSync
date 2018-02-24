const express = require('express');
const accountService = require('../services/account-service');

const router = express.Router();

router.post('/', (req, res, next) => {
    accountService
        .create(req.body.email, req.body.password)
        .then(account => res.send(account))
        .catch(errorCode => next(errorCode));
});

router.get('/:id', (req, res, next) => {
    accountService
        .get(req.params.id)
        .then(account => res.send(account))
        .catch(errorCode => next(errorCode));
});

router.delete('/:id', (req, res, next) => {
    accountService
        .remove(req.params.id)
        .then(() => res.sendStatus(200))
        .catch(errorCode => next(errorCode));
});

router.post('/track/:id', (req, res, next) => {
    accountService
        .addTrack(req.params.id, req.body.track)
        .then(account => res.send(account))
        .catch(errorCode => next(errorCode));
});

router.patch('/track/:id', (req, res, next) => {
    accountService
        .updateTrack(req.params.id, req.body.track)
        .then(account => res.send(account))
        .catch(errorCode => next(errorCode));
});

router.delete('/track/:id', (req, res, next) => {
    accountService
        .removeTrack(req.params.id, req.body.trackId)
        .then(account => res.send(account))
        .catch(errorCode => next(errorCode));
});

router.post('/playlist/:id', (req, res, next) => {
    accountService
        .createPlaylist(req.params.id, req.body.playlist)
        .then(account => res.send(account))
        .catch(errorCode => next(errorCode));
});

router.patch('/playlist/:id', (req, res, next) => {
    accountService
        .updatePlaylist(req.params.id, req.body.playlist)
        .then(account => res.send(account))
        .catch(errorCode => next(errorCode));a
});

router.delete('/playlist/:id', (req, res, next) => {
    accountService
        .removePlaylist(req.params.id, req.body.playlistId)
        .then(account => res.send(account))
        .catch(errorCode => next(errorCode));
});

router.post('/session/:id', (req, res, next) => {
    accountService
        .createSession(req.params.id, req.body.session)
        .then(account => res.send(account))
        .catch(errorCode => next(errorCode));
});

router.get('/session/state/:id', (req, res, next) => {
    accountService
        .getSessionState(req.params.id)
        .then(state => res.send({ "state": state }))
        .catch(errorCode => next(errorCode));
});

router.post('/session/start/:id', (req, res, next) => {
    accountService
        .startSession(req.params.id)
        .then(clientId => res.send(clientId))
        .catch(errorCode => next(errorCode));
});

module.exports = router;