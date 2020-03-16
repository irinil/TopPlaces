package gr.aueb.cs.project.ds.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TopPlaceInformation implements Serializable,Parcelable {
	private static final long serialVersionUID = 1731304853516621536L;
	private int totalNumberOfCheckInsToThisPlace = 0;
	private String poi = null;
	private String placeName = null;
	private String placeCategory = null;
	private List<String> placePhotos=new ArrayList<>();
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
        return "TopPlaceInformation [totalNumberOfCheckInsToThisPlace=" + totalNumberOfCheckInsToThisPlace + ", poi=" + poi
                + ", placeName=" + placeName + ", placeCategory=" + placeCategory + ", placePhotos=" + placePhotos
                + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }


	private TopPlaceInformation(Parcel in) {
		totalNumberOfCheckInsToThisPlace = in.readInt();
		poi = in.readString();
		placeName = in.readString();
		placeCategory = in.readString();
		placePhotos = new ArrayList<String>();
		in.readStringList(placePhotos);
		latitude = in.readDouble();
		longitude = in.readDouble();



	}


	public static final Parcelable.Creator<TopPlaceInformation> CREATOR
			= new Parcelable.Creator<TopPlaceInformation>() {

		// passes along the unmarshalled `Parcel`, and then returns the new object!
		@Override
		public TopPlaceInformation createFromParcel(Parcel in) {
			return new TopPlaceInformation(in);
		}

		// We just need to copy this and change the type to match our class.
		@Override
		public TopPlaceInformation[] newArray(int size) {
			return new TopPlaceInformation[size];
		}


	};


	@Override
	public int describeContents() {
		return  this.hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getTotalNumberOfCheckInsToThisPlace());
		dest.writeString(getPoi());
		dest.writeString(getPlaceName());
		dest.writeString(getPlaceCategory());
		dest.writeStringList(getPlacePhotos());
		dest.writeDouble(getLatitude());
		dest.writeDouble(getLongitude());


	}


}
