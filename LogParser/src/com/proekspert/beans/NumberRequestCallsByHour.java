package com.proekspert.beans;

public class NumberRequestCallsByHour implements Comparable<NumberRequestCallsByHour> {
	private String dateAndHour;
	private int requestsCounter;

    public String getDateAndHour() {
        return dateAndHour;
    }

    public NumberRequestCallsByHour(String dateAndHour, int requestsCounter) {
		this.dateAndHour = dateAndHour;
		this.requestsCounter = requestsCounter;
	}
	
	public void addOneMoreCall() {
		requestsCounter++;
	}



	public int getRequestsCounter() {
		return requestsCounter;
	}

	@Override
	public int compareTo(NumberRequestCallsByHour o) {
		if (o== null) {
			return 1;
		} else {
			return this.getDateAndHour().compareTo(o.dateAndHour);
		}
	}
}
