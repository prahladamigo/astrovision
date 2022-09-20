package com.astroenvision.astropower.ui.login

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.common.Utility
import com.astroenvision.astropower.models.UserRequest
import com.astroenvision.astropower.models.UserResponse
import com.astroenvision.astropower.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData


    fun userLogin(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateLogin(mobileNo: String): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(mobileNo)) {
            result = Pair(false, "Please Enter Mobile No")
        } else if (!Utility.isValidMobile(mobileNo)) {
            result = Pair(false, "Please Enter Correct Mobile No")
        }
        return result
    }

}