package com.c2.arenafinder.api.maps;

import android.app.Activity;
import android.location.GpsStatus;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


public class MapOSM implements GpsStatus.Listener, MapListener {

    private double latitude, longitude;

    private double zoomLevel;

    private MapOSMListener listener;

    private final Activity activity;

    private final MapView mMap;

    private IMapController controller;

    private MyLocationNewOverlay mMyLocationOverlay;

    public MapOSM(Activity activity, MapView mapView){
        this.activity = activity;
        this.mMap = mapView;
    }

    public void setCoordinate(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void initializeMap(double latitude, double longitude, double zoomLevel){
        setCoordinate(latitude, longitude);
        this.zoomLevel = zoomLevel;

        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMap.setExpectedCenter(new GeoPoint(this.latitude, this.longitude));
        mMap.setMultiTouchControls(true);

        mMyLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(activity), mMap);
        controller = mMap.getController();

        mMyLocationOverlay.enableMyLocation();
        mMyLocationOverlay.enableFollowLocation();
        mMyLocationOverlay.setDrawAccuracyEnabled(true);

        mMyLocationOverlay.runOnFirstFix(() -> {
            activity.runOnUiThread(() -> {
                controller.setCenter(mMyLocationOverlay.getMyLocation());
                controller.animateTo(mMyLocationOverlay.getMyLocation());
            });
        });

        controller.setZoom(zoomLevel);
        mMap.getOverlays().add(mMyLocationOverlay);
    }

    public void addMarker(double latitude, double longitude, String title, @DrawableRes int markerIcon, Runnable onClickLAction) {
        // Create a marker
        Marker marker = new Marker(mMap);
        marker.setPosition(new GeoPoint(latitude, longitude));
        marker.setTitle(title);
        marker.setIcon(ContextCompat.getDrawable(activity, markerIcon));
        marker.setInfoWindow(null);
        marker.setOnMarkerClickListener((marker1, mapView) -> {
            if (onClickLAction != null){
                onClickLAction.run();
            }
            return false;
        });

        // Show marker
        mMap.getOverlays().add(marker);
        mMap.invalidate();
    }

    public void setMapOsmListener(MapOSMListener listener){
        this.listener = listener;
    }

    @Override
    public void onGpsStatusChanged(int event) {
        if (listener != null){
            listener.onGpsStatusChanged(event);
        }
    }

    @Override
    public boolean onScroll(ScrollEvent event) {
        if (listener != null){
            listener.onScroll(event);
            return true;
        }
        return false;
    }

    @Override
    public boolean onZoom(ZoomEvent event) {
        if (listener != null){
            listener.onZoom(event);
            return true;
        }
        return false;
    }
}
