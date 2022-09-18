package com.astroenvision.astropower.retrofit

import com.astroenvision.astropower.models.UserRequest
import com.astroenvision.astropower.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/api/UserLogin")
    suspend fun userLogin(@Body userRequest: UserRequest): Response<UserResponse>

}