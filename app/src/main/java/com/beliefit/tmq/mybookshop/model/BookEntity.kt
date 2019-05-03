package com.beliefit.tmq.mybookshop.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val preview: String?,
    val title: String?,
    val subTitle: String?,
    val description: String?,
    val createdAt: String?,
    val updatedAt: String?,
    var isWished: Boolean? = false,
    var isCarted: Boolean? = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(preview)
        parcel.writeString(title)
        parcel.writeString(subTitle)
        parcel.writeString(description)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
        parcel.writeValue(isWished)
        parcel.writeValue(isCarted)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookEntity> {
        override fun createFromParcel(parcel: Parcel): BookEntity {
            return BookEntity(parcel)
        }

        override fun newArray(size: Int): Array<BookEntity?> {
            return arrayOfNulls(size)
        }
    }
}
