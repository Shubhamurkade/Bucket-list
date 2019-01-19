const Remainder = require('../apis/remainder-service');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
var config = require('../../config.json');

module.exports = function(req, res) {
    
    var payload = {
        username: req.body.username
    };
   
    var token = jwt.sign(payload, config.secret , { expiresIn: 20 });
   
    res.status(200).send({ auth: true, token: token });
}