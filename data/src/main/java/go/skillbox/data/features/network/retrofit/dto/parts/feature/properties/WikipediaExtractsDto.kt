package go.skillbox.data.features.network.retrofit.dto.parts.feature.properties

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import go.skillbox.domain.entities.parts.feature.properties.WikipediaExtracts

@JsonClass(generateAdapter = true)
data class WikipediaExtractsDto(
    @Json(name = "title") override val title: String?,
    @Json(name = "text") override val text: String?,
    @Json(name = "html") override val html: String?
) : WikipediaExtracts
