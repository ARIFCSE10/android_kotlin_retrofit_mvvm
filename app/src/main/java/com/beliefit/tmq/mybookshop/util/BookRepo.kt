package com.beliefit.tmq.mybookshop.util

import android.content.Context
import androidx.room.Room
import com.beliefit.tmq.mybookshop.model.BookEntity
import kotlin.concurrent.thread


class BookRepo(context: Context) {

    private val DB_NAME = "book_db"

    private var bookDatabase: BookDatabase? = null

    val wishdb: List<BookEntity>?
        get() = bookDatabase?.bookDao()?.allWishList
    val cartdb: List<BookEntity>?
        get() = bookDatabase?.bookDao()?.allCartList

    init {
        bookDatabase = Room.databaseBuilder(context, BookDatabase::class.java, DB_NAME).build()
    }

    fun insertOrUpdateWish(book: BookEntity, isWished: Boolean) {
        thread {
            var tbook = bookDatabase?.bookDao()?.getBook(book.id)
            if (tbook == null) {
                var bookEntity: BookEntity = BookEntity(
                    book.id,
                    book.preview,
                    book.title,
                    book.subTitle,
                    book.description,
                    book.createdAt,
                    book.updatedAt,
                    isWished = isWished
                )
                bookDatabase?.bookDao()?.insert(bookEntity)
            } else {
                tbook.isWished = isWished
                bookDatabase?.bookDao()?.update(tbook)
            }
        }
    }


    fun insertOrUpdateCart(book: BookEntity, isCarted: Boolean) {
        thread {
            var tbook = bookDatabase?.bookDao()?.getBook(book.id)
            if (tbook == null) {
                var bookEntity: BookEntity = BookEntity(
                    book.id,
                    book.preview,
                    book.title,
                    book.subTitle,
                    book.description,
                    book.createdAt,
                    book.updatedAt,
                    isCarted = isCarted
                )
                bookDatabase?.bookDao()?.insert(bookEntity)
            } else {

                tbook.isCarted = isCarted
                bookDatabase?.bookDao()?.update(tbook)
            }
        }
    }

    fun getTask(id: Int?): BookEntity? {
        return bookDatabase?.bookDao()?.getBook(id)
    }

    fun getWishStatus(id: Int?): Boolean? {
        return bookDatabase?.bookDao()?.getWishStatus(id)
    }

    fun getCartStatus(id: Int?): Boolean? {
        return bookDatabase?.bookDao()?.getCartStatus(id)
    }
}