package com.example.greendefend.domin.model.account

data class AddNewPassword(
    val email: String,
    val password: String,
    val confirmPassword: String,
)