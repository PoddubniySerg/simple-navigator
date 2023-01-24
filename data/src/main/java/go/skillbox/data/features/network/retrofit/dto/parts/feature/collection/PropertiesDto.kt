package go.skillbox.data.features.network.retrofit.dto.parts.feature.collection

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import go.skillbox.domain.entities.parts.feature.collection.Properties

@JsonClass(generateAdapter = true)
data class PropertiesDto(
    @Json(name = "xid") override val xid: String,
    @Json(name = "name") override val name: String?,
    @Json(name = "rate") override val rate: Int?,
    @Json(name = "osm") override val osmId: String?,
    @Json(name = "wikidata") override val wikidataId: String?,
    @Json(name = "kinds") override val kinds: String?
) : Properties