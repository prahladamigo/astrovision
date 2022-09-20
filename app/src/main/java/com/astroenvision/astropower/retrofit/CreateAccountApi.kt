package com.astroenvision.astropower.retrofit

import com.astroenvision.astropower.models.CreateAccountRequest
import com.astroenvision.astropower.models.CreateAccountResponse
import com.astroenvision.astropower.models.UserRequest
import com.astroenvision.astropower.models.UserResponse
import dagger.Provides
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateAccountApi {

    @POST("/api/UserRegistration")
    suspend fun createAccount(@Body createAccountRequest: CreateAccountRequest): Response<CreateAccountResponse>

}