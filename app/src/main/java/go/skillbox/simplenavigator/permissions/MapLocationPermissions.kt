package go.skillbox.simplenavigator.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class MapLocationPermissions(
    private val fragment: Fragment,
    private val startLocation: () -> Unit
) {

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private var launcher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.isNotEmpty() && map.values.all { it }) startLocation.invoke()
            else Toast.makeText(
                fragment.requireContext(),
                "Permissions are not granted",
                Toast.LENGTH_SHORT
            ).show()
        }

    fun check() {
        if (!REQUIRED_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(
                    fragment.requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            launcher.launch(REQUIRED_PERMISSIONS)
        } else {
            startLocation.invoke()
        }
    }
}