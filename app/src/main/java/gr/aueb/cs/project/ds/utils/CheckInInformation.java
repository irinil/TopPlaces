package gr.aueb.cs.project.ds.utils;

import java.io.Serializable;

public class CheckInInformation implements Serializable {
	private static final long serialVersionUID = 8018750595472621512L;
	private String poi = null;
	private String nameOfPlace = null;
	private String categoryOfPlace = null;
	private String CheckInPhotoURL = null;
	private double longitude = 0.0;
	private double latitude = 0.0;
	
	public CheckInInformation(String poi, String poiName, String poiCategory, 
			String photoURL, double latitude, double longitude) {
		this.poi = poi;
		this.nameOfPlace = poiName;
		this.categoryOfPlace = poiCategory;
		this.CheckInPhotoURL = photoURL;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getCheckInPhotoURL() {
		return this.CheckInPhotoURL;
	}
	
	public String getPoi() {
		return this.poi;
	}

	public String getNameOfPlace() {
		return this.nameOfPlace;
	}

	public String getCategoryOfPlace() {
		return this.categoryOfPlace;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}

    @Override
    public String toString() {
        return "CheckInInformation [poi=" + poi + ", nameOfPlace=" + nameOfPlace + ", categoryOfPlace="
                + categoryOfPlace + ", CheckInPhotoURL=" + CheckInPhotoURL + ", longitude=" + longitude + ", latitude="
                + latitude + "]";
    }

}
