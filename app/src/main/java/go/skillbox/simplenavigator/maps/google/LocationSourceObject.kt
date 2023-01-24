package go.skillbox.simplenavigator.maps.google

import com.google.android.gms.maps.LocationSource

class LocationSourceObject : LocationSource {

    private var locationListener: LocationSource.OnLocationChangedListener? = null

    fun getLocationListener() = locationListener

    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        locationListener = listener
    }

    override fun deactivate() {
        locationListener = null
    }
}