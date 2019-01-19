const Remainder = require('../apis/remainder-service');

module.exports = function(req, res) {
    
    var user = req.username;
    
    Remainder.update(user, req.params.id, req.body, function(err){
        if(err){
            throw err;
        }
        console.log("Update Data into MongoDB Successful");
        res.status(200);
        res.send('');
    });
}