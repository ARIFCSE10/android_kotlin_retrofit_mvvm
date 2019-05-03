package com.beliefit.tmq.mybookshop.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beliefit.tmq.mybookshop.R
import com.beliefit.tmq.mybookshop.apicontext.ApiService
import com.beliefit.tmq.mybookshop.model.UserLogin
import com.beliefit.tmq.mybookshop.model.UserLoginResponse
import com.beliefit.tmq.mybookshop.util.EmailValidator
import com.beliefit.tmq.mybookshop.util.PasswordValidator
import com.beliefit.tmq.mybookshop.util.SharedPrefUtil
import com.beliefit.tmq.mybookshop.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    fun loginResponseError(error: Throwable?) {
        loading_progressbar_login_activity.visibility = View.GONE
        Toast.makeText(this, error?.localizedMessage, Toast.LENGTH_LONG).show()
    }


    fun loginResponseSuccessfull(userLoginResponse: UserLoginResponse?) {
        loading_progressbar_login_activity.visibility = View.GONE

        SharedPrefUtil.setUserHasLoggedInPref(true, this)
        SharedPrefUtil.setUserTokenPref(userLoginResponse?.token, this)
        SharedPrefUtil.setUserNamePref(userLoginResponse?.user?.name, this)
        SharedPrefUtil.setUserEmailPref(userLoginResponse?.user?.email, this)
        Toast.makeText(this, SharedPrefUtil.getUserNamePref(this), Toast.LENGTH_LONG).show()

        gotoHomeActivity()
    }

    private fun gotoHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private lateinit var loginViewModel: LoginViewModel
    var isPasswordValid: Boolean = false
    var isEmailValid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setViewModel()
        sign_up_button_login_activity.setOnClickListener { gotoSignUpActivity() }
        setEmailAndPasswordListeners()
    }

    private fun setEmailAndPasswordListeners() {
        email_login_activity.addTextChangedListener { email ->
            loginViewModel.setEmail(email_login_activity.text)
        }

        password_login_activity.addTextChangedListener { password ->
            loginViewModel.setPassword(password_login_activity.text)
        }

        sign_in_button_login_activity.setOnClickListener {
            loading_progressbar_login_activity.visibility = View.VISIBLE
            val userData: UserLogin =
                UserLogin(email_login_activity.text.toString(), password_login_activity.text.toString())
            loginViewModel.doSignin(userData)
        }
    }


    private fun setViewModel() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        loginViewModel.loginResponseSuccessfull.observe(this, Observer<UserLoginResponse> { response ->
            loginResponseSuccessfull(response)
        })

        loginViewModel.loginResponseError.observe(this, Observer<Throwable> { error ->
            loginResponseError(error)
        })

        loginViewModel.signinValid.observe(this, Observer<Boolean> { isValid ->
            sign_in_button_login_activity.isEnabled = isValid
        })

        loginViewModel.email.observe(this, Observer<Editable> { email ->
            isEmailValid = EmailValidator().validate(email.toString())
            if (!isEmailValid) {
                emailLayout.isHelperTextEnabled = true
                emailLayout.helperText = getString(R.string.email_is_not_valid)
            } else {
                emailLayout.isHelperTextEnabled = false
            }

            loginViewModel.setSigninValidity(isEmailValid && isPasswordValid)

        })

        loginViewModel.password.observe(this, Observer<Editable> { password ->
            isPasswordValid = PasswordValidator().validate(password.toString())
            if (!isPasswordValid) {
                passwordLayout.isHelperTextEnabled = true
                passwordLayout.helperText = getString(R.string.password_is_not_valid)
            } else {
                passwordLayout.isHelperTextEnabled = false
            }

            loginViewModel.setSigninValidity(isEmailValid && isPasswordValid)
        })
    }

    override fun onDestroy() {
        ApiService.disposable?.dispose()
        super.onDestroy()
    }

    fun gotoSignUpActivity() {
        var myIntent: Intent = Intent(this, RegisterActivity::class.java)
        startActivity(myIntent)
    }

}
