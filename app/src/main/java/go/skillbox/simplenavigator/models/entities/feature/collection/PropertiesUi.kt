package go.skillbox.simplenavigator.models.entities.feature.collection

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.parts.feature.collection.Properties

data class PropertiesUi(
    override val xid: String,
    override val name: String?,
    override val rate: Int?,
    override val osmId: String?,
    override val wikidataId: String?,
    override val kinds: String?
) : Properties, Parcelable {
    constructor(parcel: Parcel) : this(
        xid = parcel.readString() ?: "",
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(xid)
        parcel.writeString(name)
        parcel.writeValue(rate)
        parcel.writeString(osmId)
        parcel.writeString(wikidataId)
        parcel.writeString(kinds)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PropertiesUi> {
        override fun createFromParcel(parcel: Parcel): PropertiesUi {
            return PropertiesUi(parcel)
        }

        override fun newArray(size: Int): Array<PropertiesUi?> {
            return arrayOfNulls(size)
        }
    }
}