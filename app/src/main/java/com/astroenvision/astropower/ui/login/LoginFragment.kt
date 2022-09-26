package com.astroenvision.astropower.ui.login

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
import com.astroenvision.astropower.common.Constants.IS_REGISTER
import com.astroenvision.astropower.common.Constants.MOBILE_NO
import com.astroenvision.astropower.common.LoadingDialog
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.common.Utility
import com.astroenvision.astropower.common.Utility.Companion.showSnackBar
import com.astroenvision.astropower.databinding.FragmentLoginBinding
import com.astroenvision.astropower.models.UserRequest
import com.astroenvision.astropower.models.UserResponse
import com.cheezycode.notesample.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val userViewModel by activityViewModels<UserViewModel>()
    private lateinit var mobileNo: String

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtContinueProceed.setOnClickListener {
            Utility.hideKeyboard(it)
            mobileNo = binding.txtMobile.text.toString()
            val validationResult = userViewModel.validateLogin(mobileNo)
            if (validationResult.first) {
                val userRequest = getUserRequest()
                userViewModel.userLogin(userRequest)
                bindObservers()
            } else {
                showValidationErrors(validationResult.second)
            }
        }
    }

    private fun getUserRequest(): UserRequest {
        return binding.run {
            UserRequest(txtMobile.text.toString())
        }
    }

    private fun showValidationErrors(error: String) {
        showSnackBar(binding.root, error)
    }

    private fun bindObservers() {
        userViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            /* val loading = LoadingDialog(requireActivity())
             loading.startLoading()*/
            binding.progressBar.isVisible = true
            when (it) {
                is NetworkResult.Success -> {
                    // loading.isDismiss()
                    binding.progressBar.isVisible = false
                    //  tokenManager.saveToken(it.data!!.token)
                    val bundle = Bundle()
                    if (it.data != null) {
                        val isRegister = it.data.isRegistered
                        bundle.putString(MOBILE_NO, mobileNo)
                        if (isRegister) {
                            findNavController().navigate(
                                R.id.action_loginFragment_to_OTPFragment,
                                bundle
                            )
                        } else {
                            findNavController().navigate(
                                R.id.action_loginFragment_to_createAccountFragment,
                                bundle
                            )

                        }
                    }
                }
                is NetworkResult.Error -> {
                    //  loading.isDismiss()
                    binding.progressBar.isVisible = false
                    showSnackBar(binding.root, it.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                    //loading.startLoading()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}