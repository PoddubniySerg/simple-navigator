package go.skillbox.simplenavigator.models.entities.feature.properties

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.parts.feature.properties.Info

data class InfoUi(
    override val src: String?,
    override val description: String?,
    override val image: String?,
    override val imageWidth: Int?,
    override val imageHeight: Int?,
    override val srcId: Int?
) : Info, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(src)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeValue(imageWidth)
        parcel.writeValue(imageHeight)
        parcel.writeValue(srcId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InfoUi> {
        override fun createFromParcel(parcel: Parcel): InfoUi {
            return InfoUi(parcel)
        }

        override fun newArray(size: Int): Array<InfoUi?> {
            return arrayOfNulls(size)
        }
    }
}
