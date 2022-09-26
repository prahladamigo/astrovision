package com.astroenvision.astropower.ui.otp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.astroenvision.astropower.R
import com.astroenvision.astropower.activities.MainActivity
import com.astroenvision.astropower.common.Constants
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.common.Utility
import com.astroenvision.astropower.databinding.FragmentOtpBinding
import com.astroenvision.astropower.models.SendOTPRequest
import com.astroenvision.astropower.models.UserRequest
import com.astroenvision.astropower.models.VerifyOTPRequest
import com.astroenvision.astropower.ui.login.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPFragment : Fragment() {
    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!

    private val sendOTPViewModel by activityViewModels<SendOTPViewModel>()
    private val otpViewModel by activityViewModels<OTPViewModel>()

    private lateinit var mobileNo: String
    private lateinit var otpValue: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mobileNo = arguments?.getString(Constants.MOBILE_NO).toString()
        val isAccountCreated = arguments?.getBoolean(Constants.IS_ACCOUNT_CREATED)

        if (isAccountCreated == true) {
            val sendOTPRequest = sendOTPRequest()
            sendOTPViewModel.sendOTP(sendOTPRequest)
            bindOTPObservers()
        }

        binding.txtOTP.setOnClickListener {
            val otp =
                binding.etOtp1.toString() + binding.etOtp2.toString() + binding.etOtp3.toString() + binding.etOtp4.toString()

            val validationResult = otpViewModel.validateOTP(otp)

            if (validationResult.first) {
                otpValue = otp
                otpViewModel.verifyOTP(getOTPRequest())
                bindObservers()
            } else {
                showValidationErrors(validationResult.second)
            }
        }

    }

    private fun bindOTPObservers() {
        sendOTPViewModel.sendOTPResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = true
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.isVisible = false
                    if (it.data != null)
                        Utility.showSnackBar(binding.root, it.data.message)
                }
                is NetworkResult.Error -> {
                    binding.progressBar.isVisible = false
                    Utility.showSnackBar(binding.root, it.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun sendOTPRequest(): SendOTPRequest {
        return binding.run {
            SendOTPRequest(mobileNo)
        }
    }

    private fun getOTPRequest(): VerifyOTPRequest {
        return binding.run {
            VerifyOTPRequest(mobileNo, otpValue)
        }
    }

    private fun showValidationErrors(error: String) {
        Utility.showSnackBar(binding.root, error)
    }

    private fun bindObservers() {
        otpViewModel.verifyOTPResponseLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = true
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.isVisible = false
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
                is NetworkResult.Error -> {
                    binding.progressBar.isVisible = false
                    Utility.showSnackBar(binding.root, it.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}