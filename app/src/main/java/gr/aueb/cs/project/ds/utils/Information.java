package gr.aueb.cs.project.ds.utils;

import java.io.Serializable;

public class Information implements Serializable {
	private static final long serialVersionUID = 6931678979705025506L;
	private DateTimeInfo date = null;
	private GeographicRectangle geographicRectangle = null;
	private int top = 0;

	public Information(String latitudeMin, String longitudeMin, String latitudeMax, String longitudeMax,
					   String fromDate,  String fromTime, String toDate, String toTime, String top) {
		this(Double.valueOf(latitudeMin), Double.valueOf(longitudeMin),
				Double.valueOf(latitudeMax), Double.valueOf(longitudeMax),
				fromDate,  fromTime, toDate, toTime, Integer.valueOf(top)
		);
	}

	public Information(double latitudeMin, double longitudeMin, double latitudeMax, double longitudeMax,
					   String fromDate, String fromTime, String toDate, String toTime, int top) {
		this.date = new DateTimeInfo(fromDate, fromTime, toDate, toTime);
		this.geographicRectangle = new GeographicRectangle(latitudeMin, longitudeMin,
				latitudeMax, longitudeMax);
		this.setTop(top);
	}

	public Information(String fromDate, String fromTime, String toDate, String toTime,
					   GeographicRectangle geographicRectangle, int top) {
		this.date = new DateTimeInfo(fromDate, fromTime, toDate, toTime);
		this.geographicRectangle = new GeographicRectangle(geographicRectangle);
		this.setTop(top);
	}

	public Information(double latitudeMin, double longitudeMin, double latitudeMax, double longitudeMax,
					   DateTimeInfo dateTimeInfo, int top) {
		this.geographicRectangle = new GeographicRectangle(latitudeMin, longitudeMin, latitudeMax, longitudeMax);
		this.date = new DateTimeInfo(dateTimeInfo);
		this.setTop(top);
	}

	public Information(GeographicRectangle geographicRectangle, DateTimeInfo date, int top) {
		this.date = new DateTimeInfo(date);
		this.geographicRectangle = new GeographicRectangle(geographicRectangle);
		this.setTop(top);
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

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

}
