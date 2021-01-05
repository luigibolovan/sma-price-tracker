package ro.upt.ac.sma.pricetracker.requests.model

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val title: String?,
    val product_url: String?,
    val product_thumbnail: String?,
    val product_price: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(product_url)
        parcel.writeString(product_thumbnail)
        parcel.writeString(product_price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}