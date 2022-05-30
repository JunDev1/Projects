package com.example.lotus_1.utilits

enum class AppStates(val state: String) {
    ONLINE("Online"),
    OFFLINE("Offline"),
    TYPING("is typing");

    companion object {
        fun updateState(appStates: AppStates) {
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_STATE).
                    setValue(appStates.state)
                .addOnSuccessListener { USER.state = appStates.state }
                .addOnFailureListener {}
        }
    }
}