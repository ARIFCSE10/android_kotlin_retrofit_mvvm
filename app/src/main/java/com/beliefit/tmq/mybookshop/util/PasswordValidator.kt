package com.beliefit.tmq.mybookshop.util

class PasswordValidator {
    fun validate(password: String): Boolean {
        if (password.length < 6) return false
        return true
    }
}