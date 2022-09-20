package com.astroenvision.astropower.models

data class CreateAccountResponse(
    val status : Boolean,
    val message : String,
    val data : String,
    val result : Int
)
