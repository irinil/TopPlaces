package gr.aueb.cs.project.ds.utils;

import java.io.Serializable;
import java.util.Arrays;

public class GeographicRectangle implements Serializable {
    private static final long serialVersionUID = 1L;
    private Coordinates[] coordinatePoint = new Coordinates[2];
    private final byte min = 0;
    private final byte max = 1;
    
    public GeographicRectangle(double latitudeMin, double longitudeMin, 
    		double latitudeMax, double longitudeMax) {
        this.coordinatePoint[min] = new Coordinates(latitudeMin, longitudeMin);//leftDown
        this.coordinatePoint[max] = new Coordinates(latitudeMax, longitudeMax);//rightUp
    }

    public GeographicRectangle(Coordinates p1, Coordinates p2) {
        this.coordinatePoint[min] = new Coordinates(p1);//leftDown
        this.coordinatePoint[max] = new Coordinates(p2);//rightUp
    }
    
    public GeographicRectangle(GeographicRectangle otherGeographicRectangle) {
        this.coordinatePoint[min] = new Coordinates(otherGeographicRectangle.getMinCoordinates());//leftDown
        this.coordinatePoint[max] = new Coordinates(otherGeographicRectangle.getMaxCoordinates());//rightUp
    }

    public Coordinates getMinCoordinates() {
       return this.coordinatePoint[min];
    }
    
    public Coordinates getMaxCoordinates() {
        return this.coordinatePoint[max];
    }

    @Override
    public String toString() {
        return "GeographicRectangle [coordinatePoint=" + Arrays.toString(coordinatePoint) + ", min=" + min + ", max="
                + max + "]";
    }
    
    
}
