package com.c2.arenafinder.api.maps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.os.Build;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.c2.arenafinder.R;
import com.c2.arenafinder.data.local.LogApp;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.Marker;


/**
 * Menampilkan Map dari tempat olahraga
 */
public class MapOSM implements GpsStatus.Listener, MapListener {

    /**
     * Default coordinate saat lokasi device tidak aktif
     */
    private static final double DEF_LATITUDE = -7.582177001317065,
            DEF_LONGITUDE = 112.13986904786631,
            DEF_ZOOM = 12.0;

    private static final long ANIMATE_SPEED = 1000L;

    private double latitude, longitude;

    private MapOSMListener listener;

    private final Activity activity;

    private final MapView mMap;

    private IMapController controller;

    private MyLocationNewOverlay mMyLocationOverlay;

    public MapOSM(Activity activity, MapView mapView) {
        this.activity = activity;
        this.mMap = mapView;

        Configuration.getInstance().load(
                activity,
                activity.getSharedPreferences(activity.getString(R.string.arenafinder_osm_map), Context.MODE_PRIVATE)
        );
    }

    /**
     * Digunakan untuk mengecek permission lokasi dari device user
     *
     * @return permission dari lokasi
     */
    private boolean checkPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    /**
     * Mengatur data coordinate dari suatu tempat
     *
     * @param latitude dari coordinate
     * @param longitude dari coordinate
     */
    public void setCoordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Digunakan untuk menginisialisasi map (menampilkan) pada halaman aplikasi
     *
     * @param latitude dari map yang ditampilkan
     * @param longitude dari map yang ditampilkan
     * @param zoomLevel dari map yang ditampilkan
     * @param centerIsMyLocation status center pada mapp
     */
    public void initializeMap(double latitude, double longitude, double zoomLevel, boolean centerIsMyLocation) {
        setCoordinate(latitude, longitude);

        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMap.setMultiTouchControls(true);
        controller = mMap.getController();

        // cek permission access location
        if (centerIsMyLocation) {
            if (checkPermissionGranted()) {
                // menampilkan map berdasarkan lokasi user
                mMyLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(activity), mMap);
                mMyLocationOverlay.enableMyLocation();
                mMyLocationOverlay.enableFollowLocation();
                mMyLocationOverlay.setDrawAccuracyEnabled(true);

                mMyLocationOverlay.runOnFirstFix(() -> activity.runOnUiThread(() -> {
                    controller.setCenter(mMyLocationOverlay.getMyLocation());
                    controller.animateTo(mMyLocationOverlay.getMyLocation());
                }));

                controller.setZoom(zoomLevel);
                mMap.getOverlays().add(mMyLocationOverlay);

            } else {
                // menampilkan map berdasarkan lokasi default jika device lokasinya tidak aktif
                setCenterMap();
            }
        } else {
            // show map center by default coordinate
            setCenterMap();
        }

    }

    /**
     * Digunakan untuk menginisialisasi map pada default coordinate
     */
    public void initializeMap() {
        initializeMap(DEF_LATITUDE, DEF_LONGITUDE, DEF_ZOOM, false);
    }

    /**
     * Mengatur titik tengah dari map yang ditampilkan
     */
    public void setCenterMap() {
        mMap.getController().animateTo(new GeoPoint(this.latitude, this.longitude), 15.0, ANIMATE_SPEED);
        mMap.invalidate();
    }

    /**
     * Mendapatkan zoom level dari map yang ditampilkan
     *
     * @return zoom level
     */
    private double getZoomLevelMarker() {
        double oldZoom = mMap.getZoomLevelDouble();

        if (oldZoom < 14.0) {
            return 14.0;
        }

        if (oldZoom < 16.0) {
            return oldZoom + 1.0;
        }
        return oldZoom;
    }

    /**
     * Menambahkan marker pada lokasi tempat tertentu, seperti contohnya tempat olahraga
     *
     * @param latitude dari coordinate lokasi
     * @param longitude dari coordinate lokasi
     * @param title dari lokasi
     * @param markerIcon dari lokasi
     * @param onClickLAction aksi saat lokasi di click
     */
    public void addMarker(double latitude, double longitude, String title, @DrawableRes int markerIcon, Runnable onClickLAction) {

        if (mMap == null){
            return;
        }

        // mengatur lokasi marker
        GeoPoint geoPoint = new GeoPoint(latitude, longitude);
        LogApp.info(activity, "ADDED Marker With Coordinate " + latitude + "," + longitude);

        // membuat marker
        Marker marker = new Marker(mMap);
        marker.setPosition(geoPoint);
        marker.setTitle(title);
        marker.setIcon(ContextCompat.getDrawable(activity, markerIcon));

        // mengatur aksi saat marker di klik
        marker.setOnMarkerClickListener((marker1, mapView) -> {
            mMap.getController().animateTo(geoPoint, getZoomLevelMarker(), ANIMATE_SPEED);
            marker.showInfoWindow();
            if (onClickLAction != null) {
                onClickLAction.run();
            }
            return true;
        });

        // Show marker
        mMap.getOverlays().add(marker);
        mMap.invalidate();
    }

    /**
     * Menghitung jarak dari suatu lokasi dengan lokasi dari user
     *
     * @param lat2 latitude dari lokasi yang dituju
     * @param lon2 longitude dari lokasi yang dituju
     * @return jarak dalam kilometer
     */
    public static double calculateDistance(double lat2, double lon2) {
        // Radius of the Earth in kilometers
        final double R = 6371.0;

        // Convert latitude and longitude from degrees to radians
        double dLat = Math.toRadians(lat2 - DEF_LATITUDE);
        double dLon = Math.toRadians(lon2 - DEF_LONGITUDE);

        // Haversine formula
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(DEF_LATITUDE)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance in kilometers
        return R * c;
    }

    /**
     * Digunakan untuk menghitung jarak tempuh dari lokasi berdasarkan jarak-nya (kilometer)
     * @param kilometers jarak dari lokasi
     *
     * @return jarak tempuh dalam menit
     */
    public static int calculateMileage(double kilometers){
        return (int) ((kilometers / 60.0) * 100);
    }

    public void setMapOsmListener(MapOSMListener listener) {
        this.listener = listener;
    }

    @Override
    public void onGpsStatusChanged(int event) {
        if (listener != null) {
            listener.onGpsStatusChanged(event);
        }
    }

    @Override
    public boolean onScroll(ScrollEvent event) {
        if (listener != null) {
            listener.onScroll(event);
            return true;
        }
        return false;
    }

    @Override
    public boolean onZoom(ZoomEvent event) {
        if (listener != null) {
            listener.onZoom(event);
            return true;
        }
        return false;
    }

}
