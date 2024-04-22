package com.example.greendefend.domin.model

data class PostModel(
    var name:String?="Fares",
    var date:String?="Now",
    var post:String?="Welcome",
    var numLike:Int?=0,
    var numDislike:Int?=0,
    var numComment:Int?=0,
)
