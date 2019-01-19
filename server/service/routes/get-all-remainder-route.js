const Remainder = require('../apis/remainder-service');

module.exports = function(req, res) {

    var user = req.username;

    Remainder.getAll(user, function(err, remainders){
        if(err){
            throw err;
        }
        console.log("Retrieve Data from MongoDB Successful");
        res.json(remainders);
    });
}