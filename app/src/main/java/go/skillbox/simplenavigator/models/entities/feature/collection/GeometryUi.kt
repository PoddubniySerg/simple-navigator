package go.skillbox.simplenavigator.models.entities.feature.collection

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.parts.feature.collection.Geometry

data class GeometryUi(
    override val type: String?,
    override val coordinates: List<Double>?
) : Geometry, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        MutableList(parcel.readInt())
        { index ->
            val array = DoubleArray(parcel.readInt())
            parcel.readDoubleArray(array)
            array[index]
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        coordinates?.size?.let {
            parcel.writeInt(it)
            parcel.writeDoubleArray(coordinates.toDoubleArray())
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GeometryUi> {
        override fun createFromParcel(parcel: Parcel): GeometryUi {
            return GeometryUi(parcel)
        }

        override fun newArray(size: Int): Array<GeometryUi?> {
            return arrayOfNulls(size)
        }
    }
}
