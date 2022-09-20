package com.astroenvision.astropower.retrofit

import com.astroenvision.astropower.models.*
import dagger.Provides
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VerifyOTPApi {

    @POST("/api/VerifyOtp")
    suspend fun verifyOtp(@Body verifyOTPRequest: VerifyOTPRequest): Response<VerifyOTPResponse>

}