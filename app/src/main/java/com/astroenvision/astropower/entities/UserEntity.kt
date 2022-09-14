package com.astroenvision.astropower.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "USER_MST")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val userName: String,
    val mobileNo: String,
    val emailId: String,
    val userPhoto: String
)
