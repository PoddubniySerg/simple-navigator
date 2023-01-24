package go.skillbox.data.repositories

import go.skillbox.data.features.network.retrofit.dto.FeaturePropertiesDto
import go.skillbox.data.features.network.retrofit.dto.FeaturesCollectionDto

interface NetworkFeaturesRepository {

    suspend fun getFeaturesByRange(
        longitudeMin: Double,
        longitudeMax: Double,
        latitudeMin: Double,
        latitudeMax: Double
    ): FeaturesCollectionDto?

    suspend fun getFeaturePropertiesById(xid: String): FeaturePropertiesDto?
}