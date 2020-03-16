package gr.aueb.cs.project.ds.utils;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private static final long serialVersionUID = 2L;
    private double latitude = 0.0;
    private double longitude = 0.0;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coordinates(Coordinates otherCoordinates) {
        this.latitude = otherCoordinates.latitude;
        this.longitude = otherCoordinates.longitude;
    }
    
    public double getLatitute() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public String toString() {
        return "Coordinates [latitude=" + latitude + ", longitude=" + longitude + "]";
    }
    
}
