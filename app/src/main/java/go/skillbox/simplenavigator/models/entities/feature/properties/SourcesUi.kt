package go.skillbox.simplenavigator.models.entities.feature.properties

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.parts.feature.properties.Sources

data class SourcesUi(
    override val geometry: String?,
    override val attributes: List<String>?
) : Sources, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(geometry)
        parcel.writeStringList(attributes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SourcesUi> {
        override fun createFromParcel(parcel: Parcel): SourcesUi {
            return SourcesUi(parcel)
        }

        override fun newArray(size: Int): Array<SourcesUi?> {
            return arrayOfNulls(size)
        }
    }
}
