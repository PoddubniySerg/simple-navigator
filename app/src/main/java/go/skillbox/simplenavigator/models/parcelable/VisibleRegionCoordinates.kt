package go.skillbox.simplenavigator.models.parcelable

import android.os.Parcel
import android.os.Parcelable

data class VisibleRegionCoordinates(
    val longitudeMin: Double,
    val longitudeMax: Double,
    val latitudeMin: Double,
    val latitudeMax: Double
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(longitudeMin)
        parcel.writeDouble(longitudeMax)
        parcel.writeDouble(latitudeMin)
        parcel.writeDouble(latitudeMax)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VisibleRegionCoordinates> {
        override fun createFromParcel(parcel: Parcel): VisibleRegionCoordinates {
            return VisibleRegionCoordinates(parcel)
        }

        override fun newArray(size: Int): Array<VisibleRegionCoordinates?> {
            return arrayOfNulls(size)
        }
    }
}
