package gr.aueb.cs.project.ds.utils;

import java.io.Serializable;

public class Information implements Serializable {
	private static final long serialVersionUID = 6931678979705025506L;
	private DateTimeInfo date = null;
    private GeographicRectangle geographicRectangle = null;
	
    public Information(String latitudeMin, String longitudeMin, String latitudeMax, String longitudeMax, 
                       String fromDate,  String fromTime, String toDate, String toTime) {
    	this(Double.valueOf(latitudeMin), Double.valueOf(longitudeMin), 
    		 Double.valueOf(latitudeMax), Double.valueOf(longitudeMax),
    		 fromDate,  fromTime, toDate, toTime
    	);
    }
    
    public Information(double latitudeMin, double longitudeMin, double latitudeMax, double longitudeMax, 
                       String fromDate, String fromTime, String toDate, String toTime) {
    	this.date = new DateTimeInfo(fromDate, fromTime, toDate, toTime);
    	this.geographicRectangle = new GeographicRectangle(latitudeMin, longitudeMin, 
    													   latitudeMax, longitudeMax);
    }
    
    public Information(String fromDate, String fromTime, String toDate, String toTime, 
                        GeographicRectangle geographicRectangle) {
		this.date = new DateTimeInfo(fromDate, fromTime, toDate, toTime);
		this.geographicRectangle = new GeographicRectangle(geographicRectangle);
	}
    
    public Information(double latitudeMin, double longitudeMin, double latitudeMax, double longitudeMax, 
            DateTimeInfo dateTimeInfo) {
            this.geographicRectangle = new GeographicRectangle(latitudeMin, longitudeMin, latitudeMax, longitudeMax);
            this.date = new DateTimeInfo(dateTimeInfo);
    }
    
    public Information(GeographicRectangle geographicRectangle, DateTimeInfo date) {
		this.date = new DateTimeInfo(date);
		this.geographicRectangle = new GeographicRectangle(geographicRectangle);
	}

	public DateTimeInfo getDateTimeInfo() {
		return this.date;
	}

	public GeographicRectangle getGeographicRectangle() {
		return this.geographicRectangle;
	}

    @Override
    public String toString() {
        return "Information [date=" + date + ", geographicRectangle=" + geographicRectangle + "]";
    }
	
}
