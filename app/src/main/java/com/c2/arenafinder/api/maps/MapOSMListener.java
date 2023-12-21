package com.c2.arenafinder.api.maps;

import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;

/**
 * Handler aksi pada map
 */
public interface MapOSMListener {

    void onGpsStatusChanged(int event);

    /**
     * Handler aksi saat map di scroll
     *
     * @param event aksi saat scroll
     * @return :v
     */
    boolean onScroll(ScrollEvent event);

    /**
     * Handler aksi saat map di zoom
     *
     * @param event aksi saat zoom
     * @return :v
     */
    boolean onZoom(ZoomEvent event);
}
