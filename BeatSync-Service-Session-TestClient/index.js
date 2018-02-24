const socket = require('socket.io-client')('http://localhost:3030/');

socket.on('connect', () => {
    socket.emit('namespace', {
        'namespace': '5a88900e2fad19efa5fcd18d',
        'pin': '0423'
    });
});

socket.on('message', (message) => console.log(message));