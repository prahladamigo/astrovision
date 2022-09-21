package com.astroenvision.astropower.retrofit

import com.astroenvision.astropower.models.*
import dagger.Provides
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OTPApi {

    @POST("/api/VerifyOtp")
    suspend fun verifyOtp(@Body verifyOTPRequest: VerifyOTPRequest): Response<VerifyOTPResponse>

    @POST("/api/SendOtp")
    suspend fun sendOTP(@Body userRequest: UserRequest): Response<UserResponse>

}