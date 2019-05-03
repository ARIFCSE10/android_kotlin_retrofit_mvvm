package com.beliefit.tmq.mybookshop.util

class EmailValidator {
    fun validate(email: String): Boolean {
        if (email.isEmpty()) return false
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) return false
        return true
    }
}
