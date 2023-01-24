package go.skillbox.data.features.network.retrofit.dto.parts.feature.properties

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import go.skillbox.domain.entities.parts.feature.properties.Point

@JsonClass(generateAdapter = true)
data class PointDto(
    @Json(name = "lon") override val longitude: Double?,
    @Json(name = "lat") override val latitude: Double?
) : Point
