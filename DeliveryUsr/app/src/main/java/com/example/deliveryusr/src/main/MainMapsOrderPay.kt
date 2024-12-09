package com.example.deliveryusr.src.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.deliveryusr.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.deliveryusr.databinding.ActivityMainMapsOrderPayBinding
import com.google.android.gms.maps.model.Marker

class MainMapsOrderPay : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private var selectedLat: Double = 0.0
    private var selectedLng: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_maps_order_pay)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val initialLocation = LatLng(-17.779, -63.182) // Ajusta esta ubicación inicial
        val marker = map.addMarker(MarkerOptions().position(initialLocation).draggable(true))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 15f))

        map.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {}
            override fun onMarkerDrag(marker: Marker) {}
            override fun onMarkerDragEnd(marker: Marker) {
                selectedLat = marker.position.latitude
                selectedLng = marker.position.longitude
            }
        })
    }
}