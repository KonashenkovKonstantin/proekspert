package com.proekspert.beans;

import java.math.BigInteger;

public class ResourceInfo implements Comparable<ResourceInfo> {
	
	private String resourceName;
	private int resourceCallCounter;
	private int averageDuration;

	private BigInteger resourceCallDurationCounter = new BigInteger("0");
	private long tmpResourceCallDurationCounter = 0;

	public ResourceInfo(String resourceName, int resourceCallCounter, int resourceCallDurationCounter) {
		this.resourceName = resourceName;
		this.resourceCallCounter = resourceCallCounter;
		this.tmpResourceCallDurationCounter = resourceCallDurationCounter;
	}

    /**
     * We can get overflow, so we have two counters
     * - the first is long. Temporary counter for quick sorting
     * - the second is BigInteger to keep number that can be more that long
     * When we detect overflow we just move current long to the BigInteger
     * @param callDuration
     */
	public void addOneMoreCall(int callDuration) {
		resourceCallCounter++;
		
		//checking for overflow
        boolean overFlowDetected = tmpResourceCallDurationCounter + callDuration < 0;
        if (overFlowDetected) {
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
	
	public int getAverageCallDuration() {
		return averageDuration; 
	}

	public String getResourceName() {
		return resourceName;
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
