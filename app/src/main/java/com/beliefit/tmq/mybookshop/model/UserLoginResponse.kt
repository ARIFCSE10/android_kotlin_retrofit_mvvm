package com.beliefit.tmq.mybookshop.model

data class UserLoginResponse(
    val error: Boolean?,
    val token: String?,
    val user: User?
)