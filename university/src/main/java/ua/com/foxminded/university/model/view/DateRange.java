package ua.com.foxminded.university.model.view;

import java.time.LocalDate;
import java.util.Date;

public class DateRange {

	String start=null;
	String end=null;

	public DateRange() {
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "DateRange [start=" + start + ", end=" + end + "]";
	}



}
