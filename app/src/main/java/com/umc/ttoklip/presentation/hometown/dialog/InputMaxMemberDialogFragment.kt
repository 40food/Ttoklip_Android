package com.umc.ttoklip.presentation.hometown.dialog

import android.os.Build
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentInputMaxMemberBinding
import com.umc.ttoklip.presentation.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputMaxMemberDialogFragment(private val btnClickListener: (Int) -> Unit) :
    BaseBottomSheetDialogFragment<FragmentInputMaxMemberBinding>(R.layout.fragment_input_max_member) {
    override fun initObserver() = Unit

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun initView() {
        this@InputMaxMemberDialogFragment.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        with(binding.memberSelector) {
            minValue = PERIOD_START
            maxValue = YEAR_END
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            selectionDividerHeight = DIVIDER_HEIGHT_ZERO
        }
        binding.chooseBtn.setOnClickListener {
            btnClickListener(binding.memberSelector.value)
            this@InputMaxMemberDialogFragment.dismiss()
        }
    }

    companion object {
        private const val PERIOD_START = 1
        private const val YEAR_END = 20
        private const val DIVIDER_HEIGHT_ZERO = 0
    }
}