package com.digitalhouse.desafio4firebase.game.service

import com.google.firebase.firestore.FirebaseFirestore

var db = FirebaseFirestore.getInstance()
var cr = db.collection("games")