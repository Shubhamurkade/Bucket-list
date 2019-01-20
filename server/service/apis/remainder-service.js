const mongoose = require('mongoose');

var remainderSchema = mongoose.Schema({
    latitude: {
        type: Number,
        default: 0.00,
        required: true 
    },
    longitude: {
        type: Number,
        default: 0.00,
        required: true 
    },
    radius: {
        type: Number,
        default: 0,
        required: true 
    },
    title: {
        type: String,
        default: ' ',
        required: true 
    },
    text: {
        type: String,
        default: ' ',
        required: true 
    },
    place: {
        type: String,
        default: ' ',
        required: true 
    },
    _id: {
        type: Number
    }
},
{
  _id: false,
  timestamps: true
});

module.exports.getAll = function(user, callback) {
    var Remainder = mongoose.model('Remainder', remainderSchema, user);
    console.log("Quering MongoDB for Data");
    Remainder.find({}, callback).sort({createdAt: -1});
}

module.exports.getById = function(user, id, callback) {
    var Remainder = mongoose.model('Remainder', remainderSchema, user);
    console.log("Quering MongoDB with given id for Data");
    Remainder.findById(id, callback);
}

module.exports.add = function(user, remainder, callback) {
    var Remainder = mongoose.model('Remainder', remainderSchema, user);
    console.log("Create Command sent to MongoDB");
    Remainder.create(remainder, callback);
}

module.exports.update = function(user, id, updatedRemainder, callback) {
    var Remainder = mongoose.model('Remainder', remainderSchema, user);
    console.log("Update Command sent to MongoDB");
    Remainder.findByIdAndUpdate(id, updatedRemainder, callback);
}

module.exports.remove = function(user, id, callback) {
    var Remainder = mongoose.model('Remainder', remainderSchema, user);
    console.log("Delete sent to MongoDB - "+id);
    Remainder.findByIdAndRemove(id, callback);
}