package go.skillbox.simplenavigator.models.entities.feature.properties

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.parts.feature.properties.Address

data class AddressUi(
    override val town: String?,
    override val state: String?,
    override val county: String?,
    override val country: String?,
    override val postcode: String?,
    override val countryCode: String?
) : Address, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(town)
        parcel.writeString(state)
        parcel.writeString(county)
        parcel.writeString(country)
        parcel.writeString(postcode)
        parcel.writeString(countryCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddressUi> {
        override fun createFromParcel(parcel: Parcel): AddressUi {
            return AddressUi(parcel)
        }

        override fun newArray(size: Int): Array<AddressUi?> {
            return arrayOfNulls(size)
        }
    }
}
