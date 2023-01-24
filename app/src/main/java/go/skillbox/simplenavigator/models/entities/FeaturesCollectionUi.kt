package go.skillbox.simplenavigator.models.entities

import android.os.Parcel
import android.os.Parcelable
import go.skillbox.domain.entities.FeaturesCollection
import go.skillbox.simplenavigator.models.entities.feature.collection.FeatureUi

data class FeaturesCollectionUi(
    override val type: String?,
    override val features: List<FeatureUi>?
) : FeaturesCollection, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(FeatureUi)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeTypedList(features)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeaturesCollectionUi> {
        override fun createFromParcel(parcel: Parcel): FeaturesCollectionUi {
            return FeaturesCollectionUi(parcel)
        }

        override fun newArray(size: Int): Array<FeaturesCollectionUi?> {
            return arrayOfNulls(size)
        }
    }
}