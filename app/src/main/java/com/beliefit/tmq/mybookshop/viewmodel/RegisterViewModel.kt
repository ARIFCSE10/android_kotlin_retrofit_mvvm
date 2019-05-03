package com.beliefit.tmq.mybookshop.viewmodel

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beliefit.tmq.mybookshop.apicontext.ApiService
import com.beliefit.tmq.mybookshop.model.UserRegister
import com.beliefit.tmq.mybookshop.model.UserRegisterResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterViewModel : ViewModel() {

    val registerResponseSuccessfull: MutableLiveData<UserRegisterResponse> by lazy {
        MutableLiveData<UserRegisterResponse>()
    }

    val registerResponseError: MutableLiveData<Throwable> by lazy {
        MutableLiveData<Throwable>()
    }

    val email: MutableLiveData<Editable> by lazy {
        MutableLiveData<Editable>()
    }

    val password: MutableLiveData<Editable> by lazy {
        MutableLiveData<Editable>()
    }

    val name: MutableLiveData<Editable> by lazy {
        MutableLiveData<Editable>()
    }

    val signupValid: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun setEmail(data: Editable) {
        email.postValue(data)
    }

    fun setName(data: Editable) {
        name.postValue(data)
    }

    fun setPassword(data: Editable) {
        password.postValue(data)
    }


    fun setSignupValidity(data: Boolean) {
        signupValid.postValue(data)
    }

    fun doSignup(userData: UserRegister) {

        ApiService.disposable = ApiService.request
            .doSignup(userData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> registerResponseSuccessfull.postValue(result) },
                { t -> registerResponseError.postValue(t) }
            )
    }
}