package com.teamxticket.xticket.data.model

import android.os.Parcel
import android.os.Parcelable

data class SaleDate(
    val adults: Int,
    val endTime: String,
    val eventId: Int,
    val maxTickets: Int,
    val price: Double,
    val saleDate: String,
    val saleDateId: Int,
    val startTime: String,
    val tickets: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(adults)
        parcel.writeString(endTime)
        parcel.writeInt(eventId)
        parcel.writeInt(maxTickets)
        parcel.writeDouble(price)
        parcel.writeString(saleDate)
        parcel.writeInt(saleDateId)
        parcel.writeString(startTime)
        parcel.writeInt(tickets)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SaleDate> {
        override fun createFromParcel(parcel: Parcel): SaleDate {
            return SaleDate(parcel)
        }

        override fun newArray(size: Int): Array<SaleDate?> {
            return arrayOfNulls(size)
        }
    }
}