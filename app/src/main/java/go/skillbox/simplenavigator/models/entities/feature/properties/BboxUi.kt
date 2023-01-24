package go.skillbox.simplenavigator.models.entities.feature.properties

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.parts.feature.properties.Bbox

data class BboxUi(
    override val longitudeMin: Double?,
    override val longitudeMax: Double?,
    override val latitudeMin: Double?,
    override val latitudeMax: Double?
) : Bbox, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(longitudeMin)
        parcel.writeValue(longitudeMax)
        parcel.writeValue(latitudeMin)
        parcel.writeValue(latitudeMax)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BboxUi> {
        override fun createFromParcel(parcel: Parcel): BboxUi {
            return BboxUi(parcel)
        }

        override fun newArray(size: Int): Array<BboxUi?> {
            return arrayOfNulls(size)
        }
    }
}
