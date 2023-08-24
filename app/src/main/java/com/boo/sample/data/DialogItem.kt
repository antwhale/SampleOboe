package com.boo.sample.data

import android.os.Parcel
import android.os.Parcelable

data class DialogItem(
    val title: String = "",
    val description: String = ""
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeString(title)
        parcel?.writeString(description)
    }

    companion object CREATOR : Parcelable.Creator<DialogItem> {
        override fun createFromParcel(parcel: Parcel?): DialogItem {
            return DialogItem(parcel?.readString().toString(), parcel?.readString().toString())
        }

        override fun newArray(size: Int): Array<DialogItem?> {
            return arrayOfNulls(size)
        }
    }
}
