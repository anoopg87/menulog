package au.com.menulog.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.os.ResultReceiver
import android.widget.EditText
import au.com.menulog.R
import au.com.menulog.service.FetchAddressIntentService
import au.com.menulog.util.LOCATION_DATA_EXTRA
import au.com.menulog.util.RECEIVER
import au.com.menulog.util.RESULT_DATA_KEY
import au.com.menulog.base.ActivityViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices

class MainVM(activity: Main) : ActivityViewModel<Main>(activity), GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private val locationPermissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    private val requestLocation = 1
    private lateinit var mGoogleApiClient: GoogleApiClient
    private var mResultReceiver: AddressResultReceiver? = null
    private var mCurrentLocation: Location? = null

    fun findRestaurants() {
        val zipCode = activity.binding?.zipcode as EditText
        if (zipCode.text.isEmpty()) zipCode.error = activity.getString(R.string.please_enter_postcode)
        else {
            loadRestaurantsListFor(zipCode.text.toString())
        }
    }

    private fun loadRestaurantsListFor(zipCode: String) {
        val intent = Intent(activity, RestaurantList::class.java)
        intent.putExtra(RestaurantListVM.OUT_CODE, zipCode)
        activity.startActivity(intent)
    }

    fun findPostCode() {
        mResultReceiver = AddressResultReceiver(Handler())

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermissions()
            } else
                initLocation()
        } else {
            initLocation()
        }
    }

    private fun initLocation() {
        val service = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if (service!!.isProviderEnabled(LocationManager.GPS_PROVIDER))
            buildGoogleApiClient()
        else
            activity.binding?.root?.let { Snackbar.make(it, activity.getString(R.string.please_enable_gps), Snackbar.LENGTH_LONG).show() }
    }

    private fun buildGoogleApiClient() {

        val resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity.applicationContext)
        if (resultCode == ConnectionResult.SUCCESS) {

            mGoogleApiClient = GoogleApiClient.Builder(activity)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()
            mGoogleApiClient.connect()

        } else if (resultCode == ConnectionResult.SERVICE_MISSING ||
                resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED ||
                resultCode == ConnectionResult.SERVICE_DISABLED) {
            val dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, activity, 1)
            dialog.show()
            dialog.setCancelable(true)

        }
    }

    private fun requestLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(this.locationPermissions, requestLocation)
        }
    }

    private fun startIntentService(mCurrentLocation: Location) {
        val intent = Intent(activity, FetchAddressIntentService::class.java)
        intent.putExtra(RECEIVER, mResultReceiver)
        intent.putExtra(LOCATION_DATA_EXTRA, mCurrentLocation)
        activity.startService(intent)
    }

    override fun onConnectionSuspended(p0: Int) {
        mGoogleApiClient.connect()
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
        if (mCurrentLocation != null) run { mCurrentLocation?.let { startIntentService(it) } }
        else activity.binding?.root?.let { Snackbar.make(it, activity.getString(R.string.location_info_not_available), Snackbar.LENGTH_LONG).show() }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        activity.binding?.root?.let { Snackbar.make(it, activity.getString(R.string.google_client_connection_failed), Snackbar.LENGTH_LONG).show() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == requestLocation) {
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    initLocation()
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    internal inner class AddressResultReceiver @SuppressLint("RestrictedApi")
    constructor(handler: Handler) : ResultReceiver(handler) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            resultData?.getString(RESULT_DATA_KEY)?.let { loadRestaurantsListFor(it) }
        }
    }

}