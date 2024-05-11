package com.alessandrasantana.gps_app;

public class Ponto {
    private double latitude;
    private double longitude;
    private double altitude;

    Ponto() {
        this.latitude  = 0.0;
        this.longitude = 0.0;
        this.altitude  = 0.0;
    }

    Ponto(double latitude, double longitude) {
        this();
        this.latitude  = latitude;
        this.longitude = longitude;
    }

    Ponto(double latitude, double longitude, double altitude) {
        this(latitude, longitude);
        this.altitude = altitude;
    }

    public double getLatitude() {return latitude;}

    public double getLongitude() {return longitude;}

    public double getAltitude() {return altitude;}

    public void setLatitude(double latitude) {this.latitude = latitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}

    public void setAltitude(double altitude) {this.altitude = altitude;}

    @Override
    public String toString() {
        return " latitude: "    + latitude +
                "\n longitude: " + longitude +
                "\n altitude: "  + altitude;
    }
}