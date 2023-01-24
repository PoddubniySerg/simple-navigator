package go.skillbox.simplenavigator.models.entities.feature.collection

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.parts.feature.collection.Feature

data class FeatureUi(
    override val id: String,
    override val type: String?,
    override val geometry: GeometryUi?,
    override val properties: PropertiesUi?
) : Feature, Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        parcel.readString(),
        parcel.readParcelable(GeometryUi::class.java.classLoader),
        parcel.readParcelable(PropertiesUi::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeParcelable(geometry, flags)
        parcel.writeParcelable(properties, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeatureUi> {
        override fun createFromParcel(parcel: Parcel): FeatureUi {
            return FeatureUi(parcel)
        }

        override fun newArray(size: Int): Array<FeatureUi?> {
            return arrayOfNulls(size)
        }
    }
}