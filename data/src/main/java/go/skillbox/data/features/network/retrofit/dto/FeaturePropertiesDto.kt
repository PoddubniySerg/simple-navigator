package go.skillbox.data.features.network.retrofit.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import go.skillbox.data.features.network.retrofit.dto.parts.feature.properties.*
import go.skillbox.domain.entities.FeatureProperties

@JsonClass(generateAdapter = true)
data class FeaturePropertiesDto(
    @Json(name = "xid") override val xid: String,
    @Json(name = "name") override val name: String?,
    @Json(name = "address") override val address: AddressDto?,
    @Json(name = "rate") override val rate: String?,
    @Json(name = "osm") override val osm: String?,
    @Json(name = "bbox") override val bbox: BboxDto?,
    @Json(name = "wikidata") override val wikidata: String?,
    @Json(name = "kinds") override val kinds: String?,
    @Json(name = "sources") override val sources: SourcesDto?,
    @Json(name = "otm") override val otmUrl: String?,
    @Json(name = "wikipedia") override val wikipediaUrl: String?,
    @Json(name = "info") override val info: InfoDto?,
    @Json(name = "image") override val imageUrl: String?,
    @Json(name = "preview") override val preview: PreviewDto?,
    @Json(name = "wikipedia_extracts") override val wikipediaExtracts: WikipediaExtractsDto?,
    @Json(name = "point") override val point: PointDto?
) : FeatureProperties