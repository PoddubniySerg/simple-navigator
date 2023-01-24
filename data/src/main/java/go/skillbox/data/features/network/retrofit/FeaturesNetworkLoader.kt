package go.skillbox.data.features.network.retrofit

import go.skillbox.data.features.network.retrofit.dto.FeaturePropertiesDto
import go.skillbox.data.features.network.retrofit.dto.FeaturesCollectionDto
import retrofit2.Response
import retrofit2.http.*

interface FeaturesNetworkLoader {

    @Headers(
        "Accept: Application/json",
        "Content-type: Application/json"
    )
    @GET("/0.1/ru/places/bbox?limit=500")
    suspend fun getFeaturesByRange(
        @Query(value = "lon_min") longitudeMin: Double,
        @Query(value = "lon_max") longitudeMax: Double,
        @Query(value = "lat_min") latitudeMin: Double,
        @Query(value = "lat_max") latitudeMax: Double,
        @Query(value = "apikey") apiKey: String
    ): Response<FeaturesCollectionDto>

    @Headers(
        "Accept: Application/json",
        "Content-type: Application/json"
    )
    @GET("/0.1/ru/places/xid/{param}")
    suspend fun getFeaturePropertiesById(
        @Path("param") xid: String,
        @Query(value = "apikey") apiKey: String
    ): Response<FeaturePropertiesDto>
}