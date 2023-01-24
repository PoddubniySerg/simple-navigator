package go.skillbox.data.features.network.retrofit.dto.parts.feature.collection

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import go.skillbox.domain.entities.parts.feature.collection.Feature

@JsonClass(generateAdapter = true)
data class FeatureDto(
    @Json(name = "id") override val id: String,
    @Json(name = "type") override val type: String?,
    @Json(name = "geometry") override val geometry: GeometryDto?,
    @Json(name = "properties") override val properties: PropertiesDto?
) : Feature