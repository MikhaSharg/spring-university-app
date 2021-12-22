package ua.com.foxminded.university.model;

import java.util.Objects;

public class LectureSessions extends IdEntity {

	private String period;
	private String startTime;
	private String endTime;

	public LectureSessions(String period, String startTime, String endTime) {
		super(null);
		this.period = period;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public LectureSessions(Long id, String period, String startTime, String endTime) {
		super(id);
		this.period = period;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(endTime, period, startTime);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LectureSessions other = (LectureSessions) obj;
		return Objects.equals(endTime, other.endTime) && Objects.equals(period, other.period)
				&& Objects.equals(startTime, other.startTime);
	}

	@Override
	public String toString() {
		return period + ": " + startTime + " - " + endTime;
	}

}
