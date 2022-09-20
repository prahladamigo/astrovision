package com.astroenvision.astropower.models

data class CreateAccountRequest(
    val User_Name : String,
    val Gender : String,
    val Email_Id : String,
    val Contact_No : String,
    val DateOfBirth : String,
    val TimeOfBirth : String,
    val Country : String,
    val State : String,
    val City : String,
    val Latitude : String,
    val Longitude : String,
    val TimeZoneInMs : String

)
