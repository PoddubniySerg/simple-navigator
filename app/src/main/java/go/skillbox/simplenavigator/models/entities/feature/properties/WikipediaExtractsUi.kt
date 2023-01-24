package go.skillbox.simplenavigator.models.entities.feature.properties

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.parts.feature.properties.WikipediaExtracts

data class WikipediaExtractsUi(
    override val title: String?,
    override val text: String?,
    override val html: String?
) : WikipediaExtracts, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(html)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WikipediaExtractsUi> {
        override fun createFromParcel(parcel: Parcel): WikipediaExtractsUi {
            return WikipediaExtractsUi(parcel)
        }

        override fun newArray(size: Int): Array<WikipediaExtractsUi?> {
            return arrayOfNulls(size)
        }
    }
}
