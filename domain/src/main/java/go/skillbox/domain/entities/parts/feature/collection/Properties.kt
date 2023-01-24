package go.skillbox.domain.entities.parts.feature.collection

interface Properties {
    val xid: String
    val name: String?
    val rate: Int?
    val osmId: String?
    val wikidataId: String?
    val kinds: String?
}