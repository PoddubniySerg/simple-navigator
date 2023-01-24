package go.skillbox.data.features.network.retrofit.dto.parts.feature.properties

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import go.skillbox.domain.entities.parts.feature.properties.Address

@JsonClass(generateAdapter = true)
data class AddressDto(
    @Json(name = "town") override val town: String?,
    @Json(name = "state") override val state: String?,
    @Json(name = "county") override val county: String?,
    @Json(name = "country") override val country: String?,
    @Json(name = "postcode") override val postcode: String?,
    @Json(name = "country_code") override val countryCode: String?
) : Address
