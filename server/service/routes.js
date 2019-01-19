'use strict'

const express = require('express');
const router = express.Router();
const VerifyToken = require('./apis/verify-token');

router.post('/generateToken', require('./routes/generate-token-route'));

router.get('/remainder/list', VerifyToken, require('./routes/get-all-remainder-route'));

router.post('/remainder', VerifyToken, require('./routes/add-remainder-route'));

router.get('/remainder/:id', VerifyToken, require('./routes/get-remainder-route'));

router.put('/remainder/:id', VerifyToken, require('./routes/update-remainder-route'));

router.delete('/remainder/:id', VerifyToken, require('./routes/delete-remainder-route'));

module.exports = router;