package com.umc.ttoklip.presentation.signup

import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivitySignupBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity:BaseActivity<ActivitySignupBinding>(R.layout.activity_signup) {
    override fun initView() {
        signupActivity=this
        
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.signup_frm)as NavHostFragment
        var navController=navHostFragment.findNavController()
        binding.signupBackIb.setOnClickListener {
            if(!navController.popBackStack()){
                finish()
            }else{
                navController.popBackStack()
            }
        }
        binding.signupCancelIb.setOnClickListener {
            binding.signupBackIb.visibility= View.VISIBLE
            binding.signupCancelIb.visibility= View.INVISIBLE
            navController.popBackStack()
        }
    }

    fun termBack(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.signup_frm)as NavHostFragment
        var navController=navHostFragment.findNavController()
        binding.signupBackIb.visibility= View.VISIBLE
        binding.signupCancelIb.visibility= View.INVISIBLE
        navController.popBackStack()
    }

    override fun initObserver()=Unit

    fun setProg(step:Int){
        binding.signupProgressbar.progress=step
    }
    fun updateButtonForTerm(){
        binding.signupBackIb.visibility= View.INVISIBLE
        binding.signupCancelIb.visibility= View.VISIBLE
    }

    companion object{
        var signupActivity: SignupActivity?=null
    }
}
