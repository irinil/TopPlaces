package gr.aueb.cs.project.ds.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TopPlaceInformation implements Serializable {
    private static final long serialVersionUID = 1731304853516621536L;
    private int totalNumberOfCheckInsToThisPlace = 0;
    private String poi = null;
    private String placeName = null;
    private String placeCategory = null;
    private List<String> placePhotos = null;
    private double latitude = 0.0;
    private double longitude = 0.0;
    
    public TopPlaceInformation(int totalNumberOfCheckInsToThisPlace, String poi, String placeName, String placeCategory,
            List<String> placePhotos, double latitude, double longitude) {
        this.totalNumberOfCheckInsToThisPlace = totalNumberOfCheckInsToThisPlace;
        this.poi = poi;
        this.placeName = placeName;
        this.placeCategory = placeCategory;
        this.placePhotos = placePhotos;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPoi() {
        return this.poi;
    }

    public String getPlaceName() {
        return this.placeName;
    }

    public String getPlaceCategory() {
        return this.placeCategory;
    }

    public List<String> getPlacePhotos() {
        return this.placePhotos;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public int getTotalNumberOfCheckInsToThisPlace() {
        return this.totalNumberOfCheckInsToThisPlace;
    }

    @Override
    public String toString() {
        return "PlaceInformation [totalNumberOfCheckInsToThisPlace=" + totalNumberOfCheckInsToThisPlace + ", poi=" + poi
                + ", placeName=" + placeName + ", placeCategory=" + placeCategory + ", placePhotos=" + placePhotos
                + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }
    
}
