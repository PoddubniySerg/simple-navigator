package go.skillbox.simplenavigator.models.entities.feature.properties

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.parts.feature.properties.Preview

data class PreviewUi(
    override val sourceUrl: String?,
    override val height: Int?,
    override val width: Int?
) : Preview, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sourceUrl)
        parcel.writeValue(height)
        parcel.writeValue(width)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PreviewUi> {
        override fun createFromParcel(parcel: Parcel): PreviewUi {
            return PreviewUi(parcel)
        }

        override fun newArray(size: Int): Array<PreviewUi?> {
            return arrayOfNulls(size)
        }
    }
}
