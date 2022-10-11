package id.hizari.common.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

/**
 * Sound Tweet - id.hizari.common.util
 *
 * Created by Hudio Hizari on 11/10/2022.
 * https://github.com/hudiohizari
 *
 */

class PermissionUtil(
    private val activity: Activity,
    private val listener: Listener
) {

    private var lastRequestedPermission = ""

    interface Listener {
        fun onPermissionUpdate(isGranted: Boolean)
    }

    private fun getRequestCodeFromPermission(permission: String): Int {
        return when (permission) {
            Manifest.permission.RECORD_AUDIO -> Constant.Permission.REQUEST_CODE_AUDIO
            else -> -1
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        STLog.d("Request result = $lastRequestedPermission")
        if (requestCode == getRequestCodeFromPermission(lastRequestedPermission)) {
            listener.onPermissionUpdate(
                (grantResults[0] == PackageManager.PERMISSION_GRANTED).also {
                    STLog.d("Request is ${if (it) "granted" else "denied"}")
                }
            )
        }
    }

    fun checkPermissions(permission: String) {
        STLog.d("Checking permission = $permission")
        lastRequestedPermission = permission

        val isGranted = ActivityCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {
            STLog.d("$permission is already granted")
            listener.onPermissionUpdate(true)
        } else {
            STLog.d("Requesting permission = $permission")
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                getRequestCodeFromPermission(permission)
            )
        }
    }

}