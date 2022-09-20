package com.astroenvision.astropower.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.models.CreateAccountRequest
import com.astroenvision.astropower.models.CreateAccountResponse
import com.astroenvision.astropower.models.VerifyOTPRequest
import com.astroenvision.astropower.models.VerifyOTPResponse
import com.astroenvision.astropower.retrofit.CreateAccountApi
import com.astroenvision.astropower.retrofit.VerifyOTPApi
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class VerifyOTPRepository @Inject constructor(private val verifyOTPApi: VerifyOTPApi) {

    private val _verifyOTPResponseLiveData = MutableLiveData<NetworkResult<VerifyOTPResponse>>()

    val verifyOTPResponseLiveData: LiveData<NetworkResult<VerifyOTPResponse>>
        get() = _verifyOTPResponseLiveData


    suspend fun verifyOTP(verifyOTPRequest: VerifyOTPRequest) {
        _verifyOTPResponseLiveData.postValue(NetworkResult.Loading())
        val response = verifyOTPApi.verifyOtp(verifyOTPRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<VerifyOTPResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _verifyOTPResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _verifyOTPResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _verifyOTPResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}