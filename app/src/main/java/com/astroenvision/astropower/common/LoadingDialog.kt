package com.astroenvision.astropower.common

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.astroenvision.astropower.R
import com.astroenvision.astropower.databinding.FragmentLoginBinding


class LoadingDialog(val mActivity: Activity) {
    private lateinit var isdialog: AlertDialog

    fun startLoading() {
        val infalter = mActivity.layoutInflater
        val dialogView = infalter.inflate(R.layout.loading_item,null)
        val bulider = AlertDialog.Builder(mActivity)
        bulider.setView(dialogView)
        bulider.setCancelable(false)
        isdialog = bulider.create()
        isdialog.show()
    }

    fun isDismiss(){
        isdialog.dismiss()
    }
}
