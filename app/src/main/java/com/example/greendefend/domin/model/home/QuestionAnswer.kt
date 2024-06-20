package com.example.greendefend.domin.model.home

import java.io.Serializable

data class QuestionAnswer(
    val question: String, val answer: String, var isVisible: Boolean = false
)
