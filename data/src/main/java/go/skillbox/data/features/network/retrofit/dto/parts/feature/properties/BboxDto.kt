package go.skillbox.data.features.network.retrofit.dto.parts.feature.properties

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import go.skillbox.domain.entities.parts.feature.properties.Bbox

@JsonClass(generateAdapter = true)
data class BboxDto(
    @Json(name = "lon_min") override val longitudeMin: Double?,
    @Json(name = "lon_max") override val longitudeMax: Double?,
    @Json(name = "lat_min") override val latitudeMin: Double?,
    @Json(name = "lat_max") override val latitudeMax: Double?
) : Bbox
