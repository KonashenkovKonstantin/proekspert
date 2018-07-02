package com.proekspert.beans;

public class NumberRequestCallsByHour implements Comparable<NumberRequestCallsByHour> {
	private String dateTime;
	private int requestsCounter;
	
	public NumberRequestCallsByHour(String dateTime, int requestsCounter) {
		this.dateTime = dateTime;
		this.requestsCounter = requestsCounter;
	}
	
	public void addOneMoreCall() {
		requestsCounter++;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public int getRequestsCounter() {
		return requestsCounter;
	}

	public void setRequestsCounter(int requestsCounter) {
		this.requestsCounter = requestsCounter;
	}

	@Override
	public int compareTo(NumberRequestCallsByHour o) {
		if (o== null) {
			return 1;
		} else {
			return this.dateTime.compareTo(o.dateTime);
		}
	}
}
