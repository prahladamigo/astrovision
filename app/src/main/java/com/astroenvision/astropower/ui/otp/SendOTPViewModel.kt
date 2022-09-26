package com.astroenvision.astropower.ui.otp

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.models.*
import com.astroenvision.astropower.repository.SendOTPRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendOTPViewModel @Inject constructor(private val sendOTPRepository: SendOTPRepository) :
    ViewModel() {

    val sendOTPResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = sendOTPRepository.sendOTPResponseLiveData


    fun sendOTP(sendOTPRequest: SendOTPRequest) {
        viewModelScope.launch {
            sendOTPRepository.sendOTP(sendOTPRequest)
        }
    }

    fun validateOTP(
        otp: String
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(otp)) {
            result = Pair(false, "Please Enter OTP")
        }
        if (otp.length < 4) {
            result = Pair(false, "Please Enter Valid OTP")
        }

        return result
    }

}