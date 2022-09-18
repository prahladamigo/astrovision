package com.astroenvision.astropower.models

data class UserResponse(
    val status : Boolean,
    val message : String,
    val data : String,
    val result : Int
)
