package go.skillbox.data.features.network.retrofit.dto.parts.feature.collection

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import go.skillbox.domain.entities.parts.feature.collection.Geometry

@JsonClass(generateAdapter = true)
data class GeometryDto(
    @Json(name = "type") override val type: String?,
    @Json(name = "coordinates") override val coordinates: List<Double>?
) : Geometry
