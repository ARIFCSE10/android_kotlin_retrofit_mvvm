package com.beliefit.tmq.mybookshop.Interface

import androidx.room.*
import com.beliefit.tmq.mybookshop.model.BookEntity


@Dao
interface BookDAO {

    @get:Query("SELECT * FROM BookEntity WHERE isWished ORDER BY id DESC")
    val allWishList: List<BookEntity>

    @get:Query("SELECT * FROM BookEntity WHERE isCarted ORDER BY id DESC")
    val allCartList: List<BookEntity>

    @Query("SELECT isWished FROM BOOKENTITY WHERE id = :id")
    fun getWishStatus(id: Int?): Boolean

    @Query("SELECT isCarted FROM BOOKENTITY WHERE id = :id")
    fun getCartStatus(id: Int?): Boolean

    @Query("SELECT * FROM BookEntity WHERE id = :id")
    fun getBook(id: Int?): BookEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(book: BookEntity)

    @Update
    fun update(book: BookEntity)
//
//    @Delete
//    fun delete(u: User)
}