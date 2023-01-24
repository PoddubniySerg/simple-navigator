package go.skillbox.domain.entities

import go.skillbox.domain.entities.parts.feature.properties.*

interface FeatureProperties {
    val xid: String
    val name: String?
    val address: Address?
    val rate: String?
    val osm: String?
    val bbox: Bbox?
    val wikidata: String?
    val kinds: String?
    val sources: Sources?
    val otmUrl: String?
    val wikipediaUrl: String?
    val info: Info?
    val imageUrl: String?
    val preview: Preview?
    val wikipediaExtracts: WikipediaExtracts?
    val point: Point?
}