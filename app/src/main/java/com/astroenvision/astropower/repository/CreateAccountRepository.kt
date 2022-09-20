package com.astroenvision.astropower.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.models.CreateAccountRequest
import com.astroenvision.astropower.models.CreateAccountResponse
import com.astroenvision.astropower.retrofit.CreateAccountApi
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class CreateAccountRepository @Inject constructor(private val createAccountApi: CreateAccountApi) {

    private val _createAccountResponseLiveData = MutableLiveData<NetworkResult<CreateAccountResponse>>()

    val createAccountResponseLiveData: LiveData<NetworkResult<CreateAccountResponse>>
        get() = _createAccountResponseLiveData


    suspend fun createAccount(createAccountRequest: CreateAccountRequest) {
        _createAccountResponseLiveData.postValue(NetworkResult.Loading())
        val response = createAccountApi.createAccount(createAccountRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<CreateAccountResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _createAccountResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _createAccountResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _createAccountResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}