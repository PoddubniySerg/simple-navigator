package go.skillbox.simplenavigator.static

enum class MapStateKeys(val key: String) {

    SPEED_LISTENER_KEY("speed"),
    VISIBLE_REGION_KEY("coordinates"),
    FEATURES_LIST_KEY("features"),
    FEATURE_XID_KEY("xid"),
    SAVE_OUT_STATE_KEY("out_state")
}