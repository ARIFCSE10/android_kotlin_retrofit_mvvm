package com.beliefit.tmq.mybookshop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beliefit.tmq.mybookshop.model.BookEntity

class DetailActivityViewModel : ViewModel() {

    val book: MutableLiveData<BookEntity> by lazy {
        MutableLiveData<BookEntity>()
    }

    val wishChecked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val cartChecked: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun setBook(book: BookEntity) {
        this.book.postValue(book)
    }
}