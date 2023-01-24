package go.skillbox.simplenavigator.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class NotificationsPermission(
    private val fragment: Fragment,
    private val createNotification: () -> Unit,
    private val sendWarning: (String) -> Unit
) {

    companion object {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.POST_NOTIFICATIONS
        )
    }

    private var launcher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.isNotEmpty() && map.values.all { it }) createNotification.invoke()
            else sendWarning("Notifications are not granted")
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun check() {
        if (!REQUIRED_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(
                    fragment.requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            launcher.launch(REQUIRED_PERMISSIONS)
        } else {
            createNotification.invoke()
        }
    }
}