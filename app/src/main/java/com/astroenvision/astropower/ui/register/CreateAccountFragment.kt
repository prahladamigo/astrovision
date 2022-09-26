package com.astroenvision.astropower.ui.register

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.astroenvision.astropower.R
import com.astroenvision.astropower.common.Constants.IS_ACCOUNT_CREATED
import com.astroenvision.astropower.common.Constants.IS_REGISTER
import com.astroenvision.astropower.common.Constants.MOBILE_NO
import com.astroenvision.astropower.common.NetworkResult
import com.astroenvision.astropower.common.Utility
import com.astroenvision.astropower.common.Utility.Companion.getMonthName
import com.astroenvision.astropower.common.Utility.Companion.hideKeyboard
import com.astroenvision.astropower.databinding.FragmentCreateAccountBinding
import com.astroenvision.astropower.models.CreateAccountRequest
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateAccountFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!
    private val createAccountViewModel by activityViewModels<CreateAccountViewModel>()

    //private val apiKey = "AIzaSyDtiD-jLquwpdgxXndk7K_eAeM3lr4_8Xc"
    private var isRegister: Boolean? = null
    private lateinit var enteredMobileNo: String

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
        val mobileNo = arguments?.getString(MOBILE_NO)
        isRegister = arguments?.getBoolean(IS_REGISTER)

        if (mobileNo != null) {
            binding.txtMobile.setText(mobileNo)
            binding.txtMobile.isEnabled = false
            enteredMobileNo = mobileNo
        }

        binding.layoutDOB.setOnClickListener(this)
        binding.txtDOB.setOnClickListener(this)
        binding.layoutBT.setOnClickListener(this)
        binding.txtBirthTime.setOnClickListener(this)

        binding.txtSinghIn.setOnClickListener {
            hideKeyboard(it)
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val userRequest = getCreateAccountRequest()
                createAccountViewModel.createAccount(userRequest)
                bindObservers()
            } else {
                showValidationErrors(validationResult.second)
            }
        }
    }

    private fun selectDOBPicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            requireActivity(), /*R.style.TimePickerTheme,*/
            { _, year, monthOfYear, dayOfMonth ->
                "$dayOfMonth-${getMonthName(monthOfYear)}-$year".also { binding.txtDOB.text = it }
            },
            year,
            month,
            day
        )
        datePicker.datePicker.maxDate = c.timeInMillis
        datePicker.show()
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
            binding.progressBar.isVisible = true
            when (it) {
                is NetworkResult.Success -> {
                    // tokenManager.saveToken(it.data!!.token)
                    binding.progressBar.isVisible = false

                    val bundle = Bundle()
                    bundle.putString(MOBILE_NO, enteredMobileNo)
                    bundle.putBoolean(IS_ACCOUNT_CREATED, true)
                    findNavController().navigate(
                        R.id.action_createAccountFragment_to_OTPFragment,
                        bundle
                    )
                }
                is NetworkResult.Error -> {
                    binding.progressBar.isVisible = false
                    showValidationErrors(it.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.layoutDOB, binding.txtDOB -> selectDOBPicker()

            binding.layoutBT, binding.txtBirthTime -> selectTimePicker()

            binding.layoutSignature, binding.spinnerSignature -> selectSignature()
        }
    }

    private fun selectSignature() {
        TODO("Not yet implemented")
    }

    private fun selectTimePicker() {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val tpd = TimePickerDialog(
            requireActivity(),
            { _, h, m ->
                binding.txtBirthTime.text = Utility.convert24To12System(h, m)
            },
            hour,
            minute,
            false
        )

        tpd.show()
    }
}
