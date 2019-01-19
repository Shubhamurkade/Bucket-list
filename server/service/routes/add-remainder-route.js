const Remainder = require('../apis/remainder-service');

module.exports = function(req, res) {

    var user = req.username;

    Remainder.add(user, req.body, function(err){
        if(err){
            throw err;
        }
        console.log("Insert Data into MongoDB Successful");
        res.status(201);
        res.send('');
    });
}