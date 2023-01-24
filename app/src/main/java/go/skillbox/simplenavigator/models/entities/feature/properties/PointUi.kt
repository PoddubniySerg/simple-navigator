package go.skillbox.simplenavigator.models.entities.feature.properties

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.parts.feature.properties.Point

data class PointUi(
    override val longitude: Double?,
    override val latitude: Double?
) : Point, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(longitude)
        parcel.writeValue(latitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PointUi> {
        override fun createFromParcel(parcel: Parcel): PointUi {
            return PointUi(parcel)
        }

        override fun newArray(size: Int): Array<PointUi?> {
            return arrayOfNulls(size)
        }
    }
}
