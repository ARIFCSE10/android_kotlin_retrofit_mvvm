package com.beliefit.tmq.mybookshop.util

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beliefit.tmq.mybookshop.Interface.BookDAO
import com.beliefit.tmq.mybookshop.model.BookEntity


@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDAO
}