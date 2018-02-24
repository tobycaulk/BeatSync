const express = require('express');
const bodyParser = require('body-parser');
const config = require('./config');

const accountRoute = require('./routes/account-route');

const app = express();
app.use(bodyParser.json());

app.use('/account/', accountRoute);

app.use((error, req, res, next) => {
    res.status(500).json({
        error: error
     });
});

app.listen(config.server.port, () => console.log("Started server on port " + config.server.port));
