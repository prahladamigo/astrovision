package com.astroenvision.astropower.common

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar


class Utility {
    companion object {

        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidMobile(mobile: String): Boolean {
            val check: Boolean
            val mobileRegex = "[6-9][0-9]{9}"
            check = mobile.matches(mobileRegex.toRegex())
            return check
        }

        fun hideKeyboard(view: View) {
            try {
                val imm =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: Exception) {

            }
        }

        fun showSnackBar(view: View, message: String) {
            val snackBar =
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            snackBar.show()
        }
    }
}