package com.example.converter.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.converter.R
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class MapsFragment : Fragment() {
    var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)
        mMapView = rootView.findViewById<View>(R.id.mapView) as MapView
        mMapView!!.onCreate(savedInstanceState)
        mMapView!!.onResume()
        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val permReqLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val granted = permissions.entries.all {
                    it.value == true
                }
                if (granted) {
                    mMapView!!.getMapAsync { mMap ->
                        googleMap = mMap
                        val currentLocationRequest =
                            CurrentLocationRequest.Builder().setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                                .build()
                        val fusedLocationClient: FusedLocationProviderClient =
                            LocationServices.getFusedLocationProviderClient(rootView.context)
                        fusedLocationClient.getCurrentLocation(currentLocationRequest, null)
                            .addOnSuccessListener { location ->
                                location?.let {
                                    if (location != null) {
                                        val myLocale = LatLng(location.latitude, location.longitude)
                                        googleMap!!.addMarker(
                                            MarkerOptions().position(myLocale).title("Родной городок")
                                                .snippet("Родной городок")
                                        )
                                        val cameraPosition =
                                            CameraPosition.Builder().target(myLocale).zoom(12f).build()
                                        googleMap!!.animateCamera(
                                            CameraUpdateFactory.newCameraPosition(
                                                cameraPosition
                                            )
                                        )
                                        Log.d("MapsLogging", "1")
                                        val coder = Geocoder(rootView.context!!, Locale.getDefault())
                                        val address = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                            coder.getFromLocation(myLocale.latitude,myLocale.longitude,1,object : Geocoder.GeocodeListener{
                                                override fun onGeocode(addresses: MutableList<Address>) {
                                                    //Страна
                                                    var country = addresses?.get(0)?.countryName
                                                    if (country == null) {
                                                        country = addresses?.get(0)?.locality
                                                        if (country == null) {
                                                            country = addresses?.get(0)?.subAdminArea
                                                        }
                                                    }
                                                    rootView.findViewById<TextView>(R.id.countryText).text = country
                                                    //Область
                                                    var area = addresses?.get(0)?.adminArea
                                                    if (area == null) {
                                                        area = addresses?.get(0)?.locality
                                                        if (area == null) {
                                                            area = addresses?.get(0)?.subAdminArea
                                                        }
                                                    }
                                                    rootView.findViewById<TextView>(R.id.AreaText).text = area

                                                    //Почтовый индекс
                                                    var postCode = addresses?.get(0)?.postalCode
                                                    if (postCode == null) {
                                                        postCode = addresses?.get(0)?.locality
                                                        if (postCode == null) {
                                                            postCode = addresses?.get(0)?.subAdminArea
                                                        }
                                                    }
                                                    rootView.findViewById<TextView>(R.id.PostCodeText).text = postCode

                                                    //Город
                                                    var city = addresses?.get(0)?.subAdminArea
                                                    if (city == null) {
                                                        city = addresses?.get(0)?.locality
                                                        if (city == null) {
                                                            city = addresses?.get(0)?.subAdminArea
                                                        }
                                                    }
                                                    rootView.findViewById<TextView>(R.id.CityText).text = city

                                                }
                                                override fun onError(errorMessage: String?) {
                                                    super.onError(errorMessage)
                                                    if (errorMessage != null) {
                                                        Log.e("Addresses Error", errorMessage )
                                                    }
                                                }
                                            })
                                        } else {
                                            @Suppress("DEPRECATION")
                                            coder.getFromLocation(myLocale.latitude, myLocale.longitude, 1)
                                        }
                                        //Координаты
                                        rootView.findViewById<TextView>(R.id.CoordinationText).text =
                                            myLocale.latitude.toString() + " : " + myLocale.longitude.toString()
                                    }
                                }
                            }
                    }
                }
            }
        permReqLauncher.launch(
            permissions
        )

        return rootView
    }
}