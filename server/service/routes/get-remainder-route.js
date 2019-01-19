const Remainder = require('../apis/remainder-service');

module.exports = function(req, res) {
    
    var user = req.username;
    
    Remainder.getById(user, req.params.id, function(err, bill){
        if(err){
            throw err;
        }
        console.log("Read Data from MongoDB Successful");
        res.json(bill);
    });
}