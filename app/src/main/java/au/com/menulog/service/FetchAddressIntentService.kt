package au.com.menulog.service

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.support.v4.os.ResultReceiver
import au.com.menulog.R
import au.com.menulog.util.*
import java.io.IOException
import java.util.*

class FetchAddressIntentService : IntentService("FetchAddress") {
    var resultReceiver: ResultReceiver? = null
    override fun onHandleIntent(intent: Intent?) {
        var errorMessage = ""
        resultReceiver = intent?.getParcelableExtra(RECEIVER)
        if (null == resultReceiver) return
        val location: Location? = intent?.getParcelableExtra(LOCATION_DATA_EXTRA)
        if (location == null) {
            errorMessage = getString(R.string.common_google_play_services_network_error_text)
            deliverResultToReceiver(FAILURE_RESULT, errorMessage)
            return
        }

        val geoCoder = Geocoder(this, Locale.getDefault())

        var addresses: List<Address>? = null

        try {
            addresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1)
        } catch (ioException: IOException) {
            errorMessage = getString(R.string.service_not_available)
        } catch (illegalArgumentException: IllegalArgumentException) {
            errorMessage = getString(R.string.service_not_available)
        }

        if (addresses == null || addresses.isEmpty()) run {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found)
            }
            deliverResultToReceiver(FAILURE_RESULT, errorMessage)
        } else {
            val address = addresses[0]
            val postCode = address.postalCode
            if (null != postCode) {
                deliverResultToReceiver(SUCCESS_RESULT, postCode)
            } else {
                deliverResultToReceiver(FAILURE_RESULT, errorMessage)
            }
        }

    }

    @SuppressLint("RestrictedApi")
    private fun deliverResultToReceiver(resultCode: Int, message: String) {
        val bundle = Bundle()
        bundle.putString(RESULT_DATA_KEY, message)
        resultReceiver?.send(resultCode, bundle)
    }
}