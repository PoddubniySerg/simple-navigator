package go.skillbox.data.features.network.retrofit.dto.parts.feature.properties

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import go.skillbox.domain.entities.parts.feature.properties.Info

@JsonClass(generateAdapter = true)
data class InfoDto(
    @Json(name = "src") override val src: String?,
    @Json(name = "descr") override val description: String?,
    @Json(name = "image") override val image: String?,
    @Json(name = "img_width") override val imageWidth: Int?,
    @Json(name = "img_height") override val imageHeight: Int?,
    @Json(name = "src_id") override val srcId: Int?
) : Info
