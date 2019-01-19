const Remainder = require('../apis/remainder-service');

module.exports = function(req, res) {
    
    var user = req.username;

    Remainder.remove(user, req.params.id, function(err){
        if(err){
            throw err;
        }
        console.log("Delete Data from MongoDB Successful");
        res.status(200);
        res.send('');
    });
}