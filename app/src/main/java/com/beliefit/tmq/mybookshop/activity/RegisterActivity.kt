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
import com.beliefit.tmq.mybookshop.model.UserRegister
import com.beliefit.tmq.mybookshop.model.UserRegisterResponse
import com.beliefit.tmq.mybookshop.util.EmailValidator
import com.beliefit.tmq.mybookshop.util.PasswordValidator
import com.beliefit.tmq.mybookshop.util.SharedPrefUtil
import com.beliefit.tmq.mybookshop.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    fun registerResponseError(error: Throwable?) {
        loading_progressbar_register_activity.visibility = View.GONE
        Toast.makeText(this, error?.localizedMessage, Toast.LENGTH_LONG).show()
    }


    fun registerResponseSuccessfull(userRegisterResponse: UserRegisterResponse?) {
        loading_progressbar_register_activity.visibility = View.GONE
        var error = userRegisterResponse?.error ?: return
        if (error) return

        SharedPrefUtil.setUserHasLoggedInPref(true, this)
        gotoLoginActivity()
    }

    private fun gotoLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }


    private lateinit var registerViewModel: RegisterViewModel
    var isPasswordValid: Boolean = false
    var isEmailValid: Boolean = false
    var isNameValid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setViewModel()
        sign_up_button_register_activity.setOnClickListener {
            registerViewModel.doSignup(
                UserRegister(
                    email_register_activity.text.toString(),
                    name_register_activity.text.toString(),
                    password_register_activity.text.toString()
                )
            )
        }
        setupListeners()
    }

    private fun setupListeners() {
        email_register_activity.addTextChangedListener { email ->
            registerViewModel.setEmail(email_register_activity.text)
        }

        name_register_activity.addTextChangedListener { name ->
            registerViewModel.setName(name_register_activity.text)
        }

        password_register_activity.addTextChangedListener { password ->
            registerViewModel.setPassword(password_register_activity.text)
        }

        sign_up_button_register_activity.setOnClickListener {
            loading_progressbar_register_activity.visibility = View.VISIBLE
            val userData = UserRegister(
                email_register_activity.text.toString(),
                name_register_activity.text.toString(),
                password_register_activity.text.toString()
            )
            registerViewModel.doSignup(userData)
        }
    }

    private fun setViewModel() {
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        registerViewModel.registerResponseSuccessfull.observe(this, Observer<UserRegisterResponse> { response ->
            registerResponseSuccessfull(response)
        })

        registerViewModel.registerResponseError.observe(this, Observer<Throwable> { error ->
            registerResponseError(error)
        })

        registerViewModel.signupValid.observe(this, Observer<Boolean> { isValid ->
            sign_up_button_register_activity.isEnabled = isValid
        })

        registerViewModel.email.observe(this, Observer<Editable> { email ->
            isEmailValid = EmailValidator().validate(email.toString())
            if (!isEmailValid) {
                emailLayout_register.isHelperTextEnabled = true
                emailLayout_register.helperText = getString(R.string.email_is_not_valid)
            } else {
                emailLayout_register.isHelperTextEnabled = false
            }

            registerViewModel.setSignupValidity(isEmailValid && isPasswordValid && isNameValid)

        })

        registerViewModel.password.observe(this, Observer<Editable> { password ->
            isPasswordValid = PasswordValidator().validate(password.toString())
            if (!isPasswordValid) {
                passwordLayout_register.isHelperTextEnabled = true
                passwordLayout_register.helperText = getString(R.string.password_is_not_valid)
            } else {
                passwordLayout_register.isHelperTextEnabled = false
            }

            registerViewModel.setSignupValidity(isEmailValid && isPasswordValid && isNameValid)
        })

        registerViewModel.name.observe(this, Observer<Editable> { name ->
            isNameValid = name.toString().isNotEmpty()
            if (!isNameValid) {
                nameLayout_register.isHelperTextEnabled = true
                nameLayout_register.helperText = getString(R.string.name_is_not_valid)
            } else {
                nameLayout_register.isHelperTextEnabled = false
            }

            registerViewModel.setSignupValidity(isEmailValid && isPasswordValid && isNameValid)
        })
    }


    override fun onDestroy() {
        ApiService.disposable?.dispose()
        super.onDestroy()
    }
}

