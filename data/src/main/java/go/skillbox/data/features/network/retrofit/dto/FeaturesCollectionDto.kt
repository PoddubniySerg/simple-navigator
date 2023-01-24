package go.skillbox.data.features.network.retrofit.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import go.skillbox.data.features.network.retrofit.dto.parts.feature.collection.FeatureDto
import go.skillbox.domain.entities.FeaturesCollection

@JsonClass(generateAdapter = true)
data class FeaturesCollectionDto(
    @Json(name = "type") override val type: String?,
    @Json(name = "features") override val features: List<FeatureDto>?
) : FeaturesCollection