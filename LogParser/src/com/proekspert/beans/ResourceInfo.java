package com.proekspert.beans;

import java.math.BigInteger;

public class ResourceInfo implements Comparable<ResourceInfo> {
	
	private String resourceName;
	private int resourceCallCounter;
	private BigInteger resourceCallDurationCounter = new BigInteger("0");
	private long tmpResourceCallDurationCounter = 0;
	private int averageDuration;

	public ResourceInfo(String resourceName, int resourceCallCounter, int resourceCallDurationCounter) {
		this.resourceName = resourceName;
		this.resourceCallCounter = resourceCallCounter;
		this.tmpResourceCallDurationCounter = resourceCallDurationCounter;
	}
	
	public void addOneMoreCall(int callDuration) {
		resourceCallCounter++;
		
		//checking for overflow
		if (tmpResourceCallDurationCounter + callDuration < 0) {
			resourceCallDurationCounter = resourceCallDurationCounter.add(new BigInteger(String.valueOf(resourceCallDurationCounter)));
			tmpResourceCallDurationCounter = callDuration;
		} else {
			tmpResourceCallDurationCounter = callDuration;
		}
	}
	
	public void countAverageDuration() {
		resourceCallDurationCounter = resourceCallDurationCounter.add(new BigInteger(String.valueOf(tmpResourceCallDurationCounter)));
		averageDuration = resourceCallDurationCounter.divide(new BigInteger(String.valueOf(resourceCallCounter))).intValue();		
	}
	
	public int getAvarageCallDuration() {		
		return averageDuration; 
	}

	public int getResourceCallCounter() {
		return resourceCallCounter;
	}

	public void setResourceCallCounter(int resourceCallCounter) {
		this.resourceCallCounter = resourceCallCounter;
	}

	public long getResourceCallDurationCounter() {
		return tmpResourceCallDurationCounter;
	}

	public void setResourceCallDurationCounter(int resourceCallDurationCounter) {
		this.tmpResourceCallDurationCounter = resourceCallDurationCounter;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public int compareTo(ResourceInfo o) {
		if (o== null) {
			return 1;
		} else {
			if (this.averageDuration == o.averageDuration) {
				return 0;
			} else if (this.averageDuration > o.averageDuration){
				return 1;
			} else {
				return -1;
			}			
		}
	}
}
