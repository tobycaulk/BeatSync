const app = require('express')();
const http = require('http').Server(app);
const io = require('socket.io')(http);
const config = require('./config');
const mongoProvider = require('./mongo-provider')(config.db.mongo.database, config.db.mongo.collection);

io.sockets.on('connection', (socket) => {
    console.log('Client connected ID[%s]', socket.id);
    socket.on('namespace', (req) => {
        mongoProvider
            .findOne({ _id: mongoProvider.objectId(req.namespace) })
            .then(account => {
                console.log('Client [%s] joined [%s] namespace', socket.id, req.namespace);
                socket.join(req.namespace);
            })
            .catch(err => console.log("Error while retrieving account with Id[" + req.namespace + "]. Error [" + err + "]"));
    });
});

http.listen(config.server.port, () => console.log("Started server on port %s", config.server.port));