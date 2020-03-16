package gr.aueb.cs.project.ds.utils;

import java.io.Serializable;

public class DateTimeInfo implements Serializable {
    private static final long serialVersionUID = 3L;
    private String fromDateTime = null;
	private String toDateTime = null;

	public DateTimeInfo(String fromDate, String fromTime, String toDate, String toTime) {
		this.fromDateTime = fromDate + " " + fromTime;
		this.toDateTime = toDate + " " + toTime;
	}
	
	public DateTimeInfo(DateTimeInfo otherDateTimeInfo) {
	    this.fromDateTime = otherDateTimeInfo.fromDateTime;
	    this.toDateTime = otherDateTimeInfo.toDateTime;
	}
	
	public String getFromDateTime() {
		return this.fromDateTime;
	}

	public String getToDateTime() {
		return this.toDateTime;
	}

    @Override
    public String toString() {
        return "DateTimeInfo [fromDateTime=" + fromDateTime + ", toDateTime=" + toDateTime + "]";
    }
	
}
