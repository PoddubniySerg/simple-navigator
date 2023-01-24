package go.skillbox.simplenavigator.models.entities

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.FeatureProperties
import go.skillbox.simplenavigator.models.entities.feature.properties.*

data class FeaturePropertiesUi(
    override val xid: String,
    override val name: String?,
    override val address: AddressUi?,
    override val rate: String?,
    override val osm: String?,
    override val bbox: BboxUi?,
    override val wikidata: String?,
    override val kinds: String?,
    override val sources: SourcesUi?,
    override val otmUrl: String?,
    override val wikipediaUrl: String?,
    override val info: InfoUi?,
    override val imageUrl: String?,
    override val preview: PreviewUi?,
    override val wikipediaExtracts: WikipediaExtractsUi?,
    override val point: PointUi?
) : FeatureProperties, Parcelable {
    constructor(parcel: Parcel) : this(
        xid = parcel.readString() ?: "",
        parcel.readString(),
        parcel.readParcelable(AddressUi::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(BboxUi::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(SourcesUi::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(InfoUi::class.java.classLoader),
        parcel.readString(),
        parcel.readParcelable(PreviewUi::class.java.classLoader),
        parcel.readParcelable(WikipediaExtractsUi::class.java.classLoader),
        parcel.readParcelable(PointUi::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(xid)
        parcel.writeString(name)
        parcel.writeParcelable(address, flags)
        parcel.writeString(rate)
        parcel.writeString(osm)
        parcel.writeParcelable(bbox, flags)
        parcel.writeString(wikidata)
        parcel.writeString(kinds)
        parcel.writeParcelable(sources, flags)
        parcel.writeString(otmUrl)
        parcel.writeString(wikipediaUrl)
        parcel.writeParcelable(info, flags)
        parcel.writeString(imageUrl)
        parcel.writeParcelable(preview, flags)
        parcel.writeParcelable(wikipediaExtracts, flags)
        parcel.writeParcelable(point, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeaturePropertiesUi> {
        override fun createFromParcel(parcel: Parcel): FeaturePropertiesUi {
            return FeaturePropertiesUi(parcel)
        }

        override fun newArray(size: Int): Array<FeaturePropertiesUi?> {
            return arrayOfNulls(size)
        }
    }
}