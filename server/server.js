const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
var http = require('http');
const app = express();
const config = require('./config.json');

// API file for various routes
const api = require('./service/routes.js');

const dbUrl = config.mongo_url || 'localhost';
const dbPort = config.mongo_port || '27017';
const dbUsername = config.mongo_username || '';
const dbPassword = config.mongo_password || '';
const appPort = config.app_port || '3000';

var uri = '';

if(dbUsername!=='' && dbPassword!==''){
    uri = 'mongodb://' +dbUsername+ ':' +dbPassword+ '@' +dbUrl+ ':' +dbPort+ '/mshack?ssl=true';    
} else {
    uri = 'mongodb://' +dbUrl+ ':' +dbPort+ '/mshack?ssl=true';
}
console.log("Establishing MongoDB Connection");
mongoose.connect(uri, { useNewUrlParser: true });

var db = mongoose.connection;
db.on('error',function (err) {  
    console.log('Mongoose default connection error: ' + err);
    process.exit(0);
}); 
  
  // When the connection is disconnected
db.on('disconnected', function () {  
    console.log('Mongoose default connection disconnected'); 
    process.exit(0);
});

// If the Node process ends, close the Mongoose connection 
process.on('SIGINT', function() {  
    mongoose.connection.close(function () { 
      process.exit(0); 
    }); 
}); 

// Parsers
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false}));

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    res.header('Access-Control-Allow-Methods', 'PUT, POST, GET, DELETE');
    next();
  });

// API location
app.use('/api', api);

// Send 404 for all other requests
app.get('*', (req, res) => {
    res.status(404);
    res.send('');
});

const server = http.createServer(app);

server.listen(appPort, () => console.log('Running BucketList on localhost:' + appPort));