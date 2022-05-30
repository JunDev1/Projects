package com.example.lotus_1.utilits

import com.example.lotus_1.models.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH: FirebaseAuth
lateinit var CURRENT_UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER:user

const val NODE_USERNAMES="usernames"
const val NODE_USERS="users"

const val CHILD_ID="id"
const val CHILD_EMAIL="email"
const val CHILD_USERNAME="username"
const val CHILD_FULLNAME="fullname"
const val CHILD_STATE="state"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = user()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
}

