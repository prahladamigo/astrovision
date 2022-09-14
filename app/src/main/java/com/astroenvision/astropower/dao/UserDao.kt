package com.astroenvision.astropower.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.astroenvision.astropower.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)

    @Query("SELECT * FROM USER_MST")
    suspend fun getUser(): UserEntity

    @Query("SELECT * FROM USER_MST")
    fun getUserList(): LiveData<List<UserEntity>>

}