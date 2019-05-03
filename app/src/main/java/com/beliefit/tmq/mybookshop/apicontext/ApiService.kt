package com.beliefit.tmq.mybookshop.apicontext

import io.reactivex.disposables.Disposable

object ApiService {

    val request by lazy {
        ApiClient().getApiService()
    }

    var disposable: Disposable? = null
}