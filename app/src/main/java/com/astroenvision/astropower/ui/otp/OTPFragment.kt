package com.astroenvision.astropower.ui.otp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.astroenvision.astropower.R
import com.astroenvision.astropower.activities.MainActivity
import com.astroenvision.astropower.common.Constants
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.common.Utility
import com.astroenvision.astropower.databinding.FragmentOtpBinding
import com.astroenvision.astropower.models.VerifyOTPRequest
import com.astroenvision.astropower.ui.login.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPFragment : Fragment() {
    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!
    private val otpViewModel by activityViewModels<OTPViewModel>()

    private lateinit var mobileNo: String
    private lateinit var otpValue: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)

        binding.txtOTP.setOnClickListener {
            //findNavController().navigate(R.id.action_createAccountFragment_to_OTPFragment)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mobileNo = arguments?.getString(Constants.MOBILE_NO).toString()

        binding.txtOTP.setOnClickListener {
            val otp =
                binding.etOtp1.toString() + binding.etOtp2.toString() + binding.etOtp3.toString() + binding.etOtp4.toString()

            val validationResult = otpViewModel.validateOTP(otp)

            if (validationResult.first) {
                otpValue = otp
                otpViewModel.verifyOTP(getOTPRequest())

            } else {
                showValidationErrors(validationResult.second)
            }

        }


    }

    private fun getOTPRequest(): VerifyOTPRequest {
        return binding.run {
            VerifyOTPRequest(mobileNo, otpValue)
        }
    }

    private fun showValidationErrors(error: String) {
        Utility.showSnackBar(binding.root, error)
        //binding.txtError.text = String.format(resources.getString(R.string.txt_error_message, error))
    }

    private fun bindObservers() {
        otpViewModel.verifyOTPResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
                is NetworkResult.Error -> {
                    Utility.showSnackBar(binding.root, it.message.toString())
                }
                is NetworkResult.Loading -> {
                    //  binding.progressBar.isVisible = false
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}