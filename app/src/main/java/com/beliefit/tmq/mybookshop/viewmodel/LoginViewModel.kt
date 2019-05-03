package com.beliefit.tmq.mybookshop.viewmodel

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beliefit.tmq.mybookshop.apicontext.ApiService
import com.beliefit.tmq.mybookshop.model.UserLogin
import com.beliefit.tmq.mybookshop.model.UserLoginResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel : ViewModel() {

    val loginResponseSuccessfull: MutableLiveData<UserLoginResponse> by lazy {
        MutableLiveData<UserLoginResponse>()
    }

    val loginResponseError: MutableLiveData<Throwable> by lazy {
        MutableLiveData<Throwable>()
    }


    val email: MutableLiveData<Editable> by lazy {
        MutableLiveData<Editable>()
    }

    val password: MutableLiveData<Editable> by lazy {
        MutableLiveData<Editable>()
    }

    val signinValid: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun setEmail(data: Editable) {
        email.postValue(data)
    }

    fun setPassword(data: Editable) {
        password.postValue(data)
    }


    fun setSigninValidity(data: Boolean) {
        signinValid.postValue(data)
    }


    fun doSignin(userData: UserLogin) {

        ApiService.disposable = ApiService.request
            .doLogin(userData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> loginResponseSuccessfull.postValue(result) },
                { t -> loginResponseError.postValue(t) }
            )
    }


}
