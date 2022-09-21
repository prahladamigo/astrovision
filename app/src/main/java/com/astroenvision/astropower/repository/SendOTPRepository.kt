package com.astroenvision.astropower.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.models.UserRequest
import com.astroenvision.astropower.models.UserResponse
import com.astroenvision.astropower.retrofit.OTPApi
import com.astroenvision.astropower.retrofit.UserApi
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class SendOTPRepository @Inject constructor(private val otpApi: OTPApi) {

    private val _otpResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _otpResponseLiveData


    suspend fun loginUser(userRequest: UserRequest) {
        _otpResponseLiveData.postValue(NetworkResult.Loading())
        val response = otpApi.sendOTP(userRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _otpResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _otpResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _otpResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}