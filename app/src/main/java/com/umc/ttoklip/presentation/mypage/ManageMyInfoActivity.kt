package com.umc.ttoklip.presentation.mypage

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityManageMyInfoBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageMyInfoActivity :
    BaseActivity<ActivityManageMyInfoBinding>(R.layout.activity_manage_my_info) {
    override fun initView() {
        initViewListener()
    }

    private fun initViewListener() {
        binding.manageMyInfoBackBtn.setOnClickListener {
            this@ManageMyInfoActivity.onBackPressedDispatcher.onBackPressed()
        }


        binding.inputIndependentCareerEt.setOnClickListener {
            val bottomSheet = InputIndependentCareerDialogFragment { year, month ->
                if (year != ZERO_CAREER && month != ZERO_CAREER) {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_base_format, year, month)
                } else if (year != ZERO_CAREER) {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_year_format, year)
                } else if (month != ZERO_CAREER) {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_month_format, month)
                } else {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_month_format, ZERO_CAREER)
                }
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.findAddressBtn.setOnClickListener {
            val intent = Intent(this, MyHometownAddressActivity::class.java)
            startActivity(intent)
        }

        binding.mainInterestGroup.setOnClickListener {
            val bottomSheet = ChooseMainInterestDialogFragment { interests ->
                binding.mainInterestGroup.removeAllViews()
                interests.forEach { interest ->
                    val chip = Chip(this)
                    chip.text = interest
                    chip.setTextAppearance(R.style.TextAppearance_App_12sp_700)
                    chip.setTextColor(ContextCompat.getColor(this, R.color.black))
                    chip.setChipBackgroundColorResource(R.color.yellow)
                    chip.setChipStrokeColorResource(R.color.yellow)
                    binding.mainInterestGroup.addView(chip)
                }
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    override fun initObserver() = Unit

    companion object {
        private const val ZERO_CAREER = 0
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}