package com.astroenvision.astropower.ui.otp

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.common.Utility
import com.astroenvision.astropower.models.*
import com.astroenvision.astropower.repository.CreateAccountRepository
import com.astroenvision.astropower.repository.UserRepository
import com.astroenvision.astropower.repository.VerifyOTPRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OTPViewModel @Inject constructor(private val verifyOTPRepository: VerifyOTPRepository) :
    ViewModel() {

    val verifyOTPResponseLiveData: LiveData<NetworkResult<VerifyOTPResponse>>
        get() = verifyOTPRepository.verifyOTPResponseLiveData


    fun verifyOTP(verifyOTPRequest: VerifyOTPRequest) {
        viewModelScope.launch {
            verifyOTPRepository.verifyOTP(verifyOTPRequest)
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