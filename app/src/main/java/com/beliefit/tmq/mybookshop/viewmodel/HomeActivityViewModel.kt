package com.beliefit.tmq.mybookshop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beliefit.tmq.mybookshop.apicontext.ApiService
import com.beliefit.tmq.mybookshop.model.BookEntity
import com.beliefit.tmq.mybookshop.model.BookListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeActivityViewModel : ViewModel() {
    val bookListResponseSuccessful: MutableLiveData<BookListResponse> by lazy {
        MutableLiveData<BookListResponse>()
    }

    val bookListResponseError: MutableLiveData<Throwable> by lazy {
        MutableLiveData<Throwable>()
    }

    val bookEntity: MutableLiveData<List<BookEntity>> by lazy {
        MutableLiveData<List<BookEntity>>()
    }


    fun fetchBookListData() {
        ApiService.disposable = ApiService.request
            .doFetchBookList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> bookListResponseSuccessful.postValue(result) },
                { t -> bookListResponseError.postValue(t) }
            )
    }
}