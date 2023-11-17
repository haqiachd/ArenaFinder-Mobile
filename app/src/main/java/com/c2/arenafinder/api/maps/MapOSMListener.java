package com.c2.arenafinder.api.maps;

import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;

public interface MapOSMListener {

    void onGpsStatusChanged(int event);

    boolean onScroll(ScrollEvent event);

    boolean onZoom(ZoomEvent event);
}
