package com.example.greendefend.domin.model.account

data class ChangePassword(
    val currentPassword: String,
    val email: String,
    val newPassword: String
)