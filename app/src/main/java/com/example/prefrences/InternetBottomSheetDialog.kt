package com.example.prefrences

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.loginfirebasedemo.R
import com.example.loginfirebasedemo.databinding.NoInternetLayoutBinding
import com.example.traintraking.prefrences.PrefrencesApplicaton
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InternetBottomSheetDialog : BottomSheetDialogFragment() {
    var binding: NoInternetLayoutBinding? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.no_internet_layout, container, false)
        isCancelable = false
       requireActivity(). window.setStatusBarColor(this.getResources().getColor(R.color.df))
        binding?.cta?.setOnClickListener {
            binding?.progressbar?.visibility = VISIBLE
            if (DeviceUtils.isOnline(PrefrencesApplicaton.appInstance)) {
                dismiss()
            }
            Handler(Looper.getMainLooper()).postDelayed({
                binding?.progressbar?.visibility = GONE

            }, 800)
        }

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        if (DeviceUtils.isOnline(PrefrencesApplicaton.appInstance)) {
            dismiss()
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it1 ->
                val behaviour = BottomSheetBehavior.from(it1)
                setupFullHeight(it1)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED

                behaviour.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_DRAGGING) {
                            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                        }
                    }

                    override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
                        // React to dragging events
                    }
                })
            }
        }

        return dialog
    }

}