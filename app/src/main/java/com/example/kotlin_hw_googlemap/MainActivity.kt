package com.example.kotlin_hw_googlemap

import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Transformations.map
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val REQUEST_PERMISSIONS = 1
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.isEmpty()) return
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                for (result in grantResults)
                //若使用者拒絕給予權限則關閉APP
                    if (result != PackageManager.PERMISSION_GRANTED)
                        finish()
                    else{
                        //連接MapFragment物件
                        val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                        map.getMapAsync(this)
                    }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS)
        else{
            //連接MapFragment物件
            val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            map.getMapAsync(this)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return
        map.isMyLocationEnabled = true

        val m1 = MarkerOptions()
        m1.position(LatLng(25.033611, 121.565000))
        m1.title("台北101")
        m1.draggable(true)
        map.addMarker(m1)

        val m2 = MarkerOptions()
        m2.position(LatLng(25.047924, 121.517081))
        m2.title("台北車站")
        m2.draggable(true)
        map.addMarker(m2)

        val polylineOpt = PolylineOptions()
        polylineOpt.add(LatLng(25.033611, 121.565000))
        polylineOpt.add(LatLng(25.032728, 121.565137))
        polylineOpt.add(LatLng(25.047924, 121.517081))
        polylineOpt.color(Color.BLUE)
        val polyline = map.addPolyline(polylineOpt)
        polyline.width = 10f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.04, 121.54), 13f))
    }
}