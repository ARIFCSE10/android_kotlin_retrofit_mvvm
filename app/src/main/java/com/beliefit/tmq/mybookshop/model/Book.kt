package com.beliefit.tmq.mybookshop.model

import android.os.Parcel
import android.os.Parcelable

data class Book(
    val createdAt: String?,
    val description: String?,
    val id: Int?,
    val preview: String?,
    val subTitle: String?,
    val title: String?,
    val updatedAt: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(createdAt)
        parcel.writeString(description)
        parcel.writeValue(id)
        parcel.writeString(preview)
        parcel.writeString(subTitle)
        parcel.writeString(title)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}