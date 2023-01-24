package go.skillbox.simplenavigator.utils

import go.skillbox.domain.entities.FeatureProperties
import go.skillbox.domain.entities.FeaturesCollection
import go.skillbox.domain.entities.parts.feature.collection.Feature
import go.skillbox.domain.entities.parts.feature.collection.Geometry
import go.skillbox.domain.entities.parts.feature.collection.Properties
import go.skillbox.domain.entities.parts.feature.properties.*
import go.skillbox.simplenavigator.models.entities.FeaturePropertiesUi
import go.skillbox.simplenavigator.models.entities.FeaturesCollectionUi
import go.skillbox.simplenavigator.models.entities.feature.collection.FeatureUi
import go.skillbox.simplenavigator.models.entities.feature.collection.GeometryUi
import go.skillbox.simplenavigator.models.entities.feature.collection.PropertiesUi
import go.skillbox.simplenavigator.models.entities.feature.properties.*
import javax.inject.Inject

class Converter @Inject constructor() {

    fun convert(featuresCollection: FeaturesCollection): FeaturesCollectionUi {
        return FeaturesCollectionUi(
            featuresCollection.type,
            featuresCollection.features?.let {
                it.map { feature ->
                    convert(feature)
                }
            }
        )
    }

    fun convert(featureProperties: FeatureProperties): FeaturePropertiesUi {
        return FeaturePropertiesUi(
            featureProperties.xid,
            featureProperties.name,
            featureProperties.address?.let { convert(it) },
            featureProperties.rate,
            featureProperties.osm,
            featureProperties.bbox?.let { convert(it) },
            featureProperties.wikidata,
            featureProperties.kinds,
            featureProperties.sources?.let { convert(it) },
            featureProperties.otmUrl,
            featureProperties.wikipediaUrl,
            featureProperties.info?.let { convert(it) },
            featureProperties.imageUrl,
            featureProperties.preview?.let { convert(it) },
            featureProperties.wikipediaExtracts?.let { convert(it) },
            featureProperties.point?.let { convert(it) }
        )
    }

    private fun convert(address: Address): AddressUi {
        return AddressUi(
            address.town,
            address.state,
            address.county,
            address.country,
            address.postcode,
            address.countryCode
        )
    }

    private fun convert(bBox: Bbox): BboxUi {
        return BboxUi(
            bBox.longitudeMin,
            bBox.longitudeMax,
            bBox.latitudeMin,
            bBox.latitudeMax
        )
    }

    private fun convert(info: Info): InfoUi {
        return InfoUi(
            info.src,
            info.description,
            info.image,
            info.imageWidth,
            info.imageHeight,
            info.srcId
        )
    }

    private fun convert(point: Point): PointUi {
        return PointUi(
            point.longitude,
            point.latitude
        )
    }

    private fun convert(preview: Preview): PreviewUi {
        return PreviewUi(
            preview.sourceUrl,
            preview.height,
            preview.width
        )
    }

    private fun convert(sources: Sources): SourcesUi {
        return SourcesUi(
            sources.geometry,
            sources.attributes
        )
    }

    private fun convert(wikipediaExtracts: WikipediaExtracts): WikipediaExtractsUi {
        return WikipediaExtractsUi(
            wikipediaExtracts.title,
            wikipediaExtracts.text,
            wikipediaExtracts.html
        )
    }

    private fun convert(geometry: Geometry): GeometryUi {
        return GeometryUi(
            geometry.type,
            geometry.coordinates
        )
    }

    private fun convert(properties: Properties): PropertiesUi {
        return PropertiesUi(
            properties.xid,
            properties.name,
            properties.rate,
            properties.osmId,
            properties.wikidataId,
            properties.kinds
        )
    }

    private fun convert(feature: Feature): FeatureUi {
        return FeatureUi(
            feature.id,
            feature.type,
            feature.geometry?.let { convert(it) },
            feature.properties?.let { convert(it) }
        )
    }
}