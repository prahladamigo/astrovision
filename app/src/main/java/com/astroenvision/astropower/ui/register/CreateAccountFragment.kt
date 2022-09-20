package com.astroenvision.astropower.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.astroenvision.astropower.R
import com.astroenvision.astropower.common.Constants.MOBILE_NO
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.common.Utility
import com.astroenvision.astropower.common.Utility.Companion.hideKeyboard
import com.astroenvision.astropower.databinding.FragmentCreateAccountBinding
import com.astroenvision.astropower.models.CreateAccountRequest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!
    private val createAccountViewModel by activityViewModels<CreateAccountViewModel>()
    private val apiKey = "AIzaSyDtiD-jLquwpdgxXndk7K_eAeM3lr4_8Xc"
    private var mobileNo : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* // Initialize the SDK
        if(!Places.isInitialized()) {
            Places.initialize(context, apiKey)
        }
        // Create a new PlacesClient instance
        val placesClient = context?.let { Places.createClient(it) }

        binding.txtBirthPlace.setOnClickListener {
           // List<Place.Field>
            val fieldList = mutableListOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(context)
            startActivity(intent)
        }
*/
         mobileNo = arguments?.getString(MOBILE_NO)

        if (mobileNo != null)
            binding.txtMobile.setText(mobileNo)

        binding.txtSinghIn.setOnClickListener {
            hideKeyboard(it)
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val userRequest = getCreateAccountRequest()
                createAccountViewModel.createAccount(userRequest)
            } else {
                showValidationErrors(validationResult.second)
            }
        }
        bindObservers()
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val name = binding.txtName.text.toString()
        val mobile = binding.txtMobile.text.toString()
        val email = binding.txtEmail.text.toString()
        val dob = binding.txtDOB.text.toString()
        val birthTime = binding.txtBirthTime.text.toString()
        val birthPlace = binding.txtBirthTime.text.toString()
        return createAccountViewModel.validateCreateAccount(
            name,
            mobile,
            email,
            dob,
            birthTime,
            birthPlace,
            false
        )
    }

    private fun showValidationErrors(error: String) {
        Utility.showSnackBar(binding.root, error)
        //binding.txtError.text = String.format(resources.getString(R.string.txt_error_message, error))
    }


    private fun getCreateAccountRequest(): CreateAccountRequest {
        return binding.run {
            CreateAccountRequest(
                txtName.text.toString(),
                txtMobile.text.toString(),
                txtEmail.text.toString(),
                txtDOB.text.toString(),
                txtBirthTime.text.toString(),
                txtBirthPlace.text.toString(),
                txtEmail.text.toString(),
                "",
                "",
                "",
                "",
                ""
            )
        }
    }

    private fun bindObservers() {
        createAccountViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            // binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    // tokenManager.saveToken(it.data!!.token)
                    val bundle = Bundle()
                    bundle.putString(MOBILE_NO,binding.txtMobile.toString())
                    findNavController().navigate(R.id.action_createAccountFragment_to_OTPFragment, bundle)
                }
                is NetworkResult.Error -> {
                    showValidationErrors(it.message.toString())
                }
                is NetworkResult.Loading -> {
                    // binding.progressBar.isVisible = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}