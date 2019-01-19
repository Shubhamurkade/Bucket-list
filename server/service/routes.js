'use strict'

const express = require('express');
const router = express.Router();

router.get('/generateToken', require('./routes/generate-token-route'));

router.get('/remainder/list', require('./routes/get-all-remainder-route'));

router.post('/remainder', require('./routes/add-remainder-route'));

router.get('/remainder/:id', require('./routes/get-remainder-route'));

router.put('/remainder/:id', require('./routes/update-remainder-route'));

router.delete('/remainder/:id', require('./routes/delete-remainder-route'));

module.exports = router;