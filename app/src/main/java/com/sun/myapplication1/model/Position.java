package com.sun.myapplication1.model;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Position extends LitePalSupport {
    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
