package com.astroenvision.astropower.ui.register

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.common.Utility
import com.astroenvision.astropower.models.CreateAccountRequest
import com.astroenvision.astropower.models.CreateAccountResponse
import com.astroenvision.astropower.models.UserRequest
import com.astroenvision.astropower.models.UserResponse
import com.astroenvision.astropower.repository.CreateAccountRepository
import com.astroenvision.astropower.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(private val createAccountRepository: CreateAccountRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<CreateAccountResponse>>
        get() = createAccountRepository.createAccountResponseLiveData


    fun createAccount(createAccountRequest: CreateAccountRequest) {
        viewModelScope.launch {
            createAccountRepository.createAccount(createAccountRequest)
        }
    }

    fun validateCreateAccount(
        name: String,
        mobile: String,
        email: String,
        dob: String,
        birthTime: String,
        birthPlace: String,
        b: Boolean
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(name)) {
            result = Pair(false, "Please Enter Your Name")
        } else if (TextUtils.isEmpty(mobile)) {
            result = Pair(false, "Please Enter Mobile No")
        } else if (!Utility.isValidMobile(mobile)) {
            result = Pair(false, "Please Enter Valid Mobile No")
        }else if (TextUtils.isEmpty(email)) {
            result = Pair(false, "Please Enter Email Id")
        } else if (!Utility.isValidEmail(email)) {
            result = Pair(false, "Please Enter Valid Email Id")
        } else if (TextUtils.isEmpty(dob)) {
            result = Pair(false, "Please Enter Date of Birth")
        } else if (!Utility.isValidMobile(birthTime)) {
            result = Pair(false, "Please Enter Birth Time")
        }else if (TextUtils.isEmpty(birthPlace)) {
            result = Pair(false, "Please Enter Place")
        }

        return result
    }

}