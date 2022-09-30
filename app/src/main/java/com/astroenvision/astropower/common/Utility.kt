package com.astroenvision.astropower.common

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.util.Patterns
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.astroenvision.astropower.MyApplication
import com.astroenvision.astropower.R
import com.google.android.material.snackbar.Snackbar


class Utility {
    private val TAG: String = Utility::class.java.simpleName
    private val MY_PREF_NAME : String = "AstroPower"

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

        fun getMonthName(monthNum: Int): String? {
            var monthname = "Jan"
            when (monthNum) {
                0 -> monthname = "Jan"
                1 -> monthname = "Feb"
                2 -> monthname = "Mar"
                3 -> monthname = "Apr"
                4 -> monthname = "May"
                5 -> monthname = "Jun"
                6 -> monthname = "Jul"
                7 -> monthname = "Aug"
                8 -> monthname = "Sep"
                9 -> monthname = "Oct"
                10 -> monthname = "Nov"
                11 -> monthname = "Dec"
            }
            return monthname
        }

        fun convert24To12System(hour: Int, minute: Int): String? {
            var hour = hour
            var time = ""
            var am_pm = ""
            if (hour < 12) {
                if (hour == 0) hour = 12
                am_pm = "AM"
            } else {
                if (hour != 12) hour -= 12
                am_pm = "PM"
            }
            var h = hour.toString() + ""
            var m = minute.toString() + ""
            if (h.length == 1) h = "0$h"
            if (m.length == 1) m = "0$m"
            time = "$h:$m $am_pm"
            return time
        }


        fun showProgressDialog(context: Context, text : String) {
            val llPadding = 30
            val ll = LinearLayout(context)
            ll.orientation = LinearLayout.HORIZONTAL
            ll.setPadding(llPadding, llPadding, llPadding, llPadding)
            ll.gravity = Gravity.CENTER
            var llParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            llParam.gravity = Gravity.CENTER
            ll.layoutParams = llParam
            val progressBar = ProgressBar(context)
            progressBar.isIndeterminate = true
            progressBar.setPadding(0, 0, llPadding, 0)
            progressBar.layoutParams = llParam
            llParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            llParam.gravity = Gravity.CENTER
            val tvText = TextView(context)
            tvText.text = text
            tvText.setTextColor(Color.parseColor("#000000"))
            tvText.textSize = 20f
            tvText.layoutParams = llParam
            ll.addView(progressBar)
            ll.addView(tvText)
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            builder.setView(ll)
            val dialog: AlertDialog = builder.create()
            dialog.show()
            val window: Window? = dialog.window
            if (window != null) {
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(dialog.window!!.attributes)
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                dialog.window!!.attributes = layoutParams
            }
        }

        fun progressDialogWithMessage(context: Context, message: String): Dialog {
            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            val dialogMessage = dialog.findViewById<TextView>(R.id.progressText)
            dialogMessage.text = message
            dialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT))
            return dialog
        }

        fun progressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT))
            return dialog
        }




    }




}