package go.skillbox.data.features.network.retrofit

import go.skillbox.data.DataApp
import go.skillbox.data.R
import go.skillbox.data.features.network.retrofit.dto.FeaturePropertiesDto
import go.skillbox.data.features.network.retrofit.dto.FeaturesCollectionDto
import go.skillbox.data.repositories.NetworkFeaturesRepository

open class FeaturesDataSource(
    private val apiService: FeaturesNetworkLoader
) : NetworkFeaturesRepository {

    override suspend fun getFeaturesByRange(
        longitudeMin: Double,
        longitudeMax: Double,
        latitudeMin: Double,
        latitudeMax: Double
    ): FeaturesCollectionDto? {
        val response = apiService.getFeaturesByRange(
            longitudeMin,
            longitudeMax,
            latitudeMin,
            latitudeMax,
            DataApp.getContext()?.getString(R.string.open_trip_map_api_key)
                ?: throw RuntimeException("Application context not found")
        )
        println(response)
        println(response.body())
        if (response.isSuccessful) return response.body()
        throw RuntimeException(response.message())
    }

    override suspend fun getFeaturePropertiesById(xid: String): FeaturePropertiesDto? {
        val response = apiService.getFeaturePropertiesById(
            xid,
            DataApp.getContext()?.getString(R.string.open_trip_map_api_key)
                ?: throw RuntimeException("Application context not found")
        )
        println(response)
        println(response.body())
        if (response.isSuccessful) return response.body()
        throw RuntimeException(response.message())
    }
}