package com.beliefit.tmq.mybookshop.model

data class BookListResponse(
    val `data`: List<BookEntity?>?,
    val error: Boolean?
)