package go.skillbox.data.features.network.retrofit.dto.parts.feature.properties

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import go.skillbox.domain.entities.parts.feature.properties.Sources

@JsonClass(generateAdapter = true)
data class SourcesDto(
    @Json(name = "geometry") override val geometry: String?,
    @Json(name = "attributes") override val attributes: List<String>?
) : Sources
