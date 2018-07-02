package com.proekspert.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.proekspert.beans.NumberRequestCallsByHour;
import com.proekspert.beans.ResourceInfo;
import com.proekspert.content.LanguageVariables;
import com.proekspert.utils.LogParserUtils;

public class LogParser implements Runnable {
	
	//temporary structures
	int minNumberOfRequestByHour = 0;
	int maxNumberOfRequestByHour = 0;
	private Map<String, NumberRequestCallsByHour> numberRequestCallsByHourMap = new HashMap<>();
	private Map<String, ResourceInfo> uniqueResourceVsDetailedInfoMap = new HashMap<>();	
	
	//final result structures
	private List<ResourceInfo> resourcesByCallDuration;
	private List<NumberRequestCallsByHour> numberRequestCallsByHour;
	
	
	private static final Pattern LINE_IN_LOG = 
			Pattern.compile("^\\d\\d\\d\\d-\\d\\d-\\d\\d\\s+" 	//date
					+ "\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d\\s+" 		//time
					+ "\\(.*\\)\\s+"							//thread-id (in brackets)
					+ ".* in " 									//URI + query string, query may contain spaces => 
																//we go through all possible characters till ' in'
					+ "\\d+"									//duration
					+ "$");
	
	private String filePathString;
	
	public LogParser(String filePathString) {
		this.filePathString = filePathString;
	}
	
	public void run() {
		try(Scanner scan = new Scanner(new File(filePathString));) {
			while(scan.hasNext()) {
				parseLine(scan.nextLine());
			}
		} catch (Exception ee) {			
			System.out.println(LanguageVariables.ERROR_SMTH_WENT_WRONG);
			ee.printStackTrace();
		}
		
		//2. count average => make new list of resource etc
		resourcesByCallDuration =  new ArrayList<>(uniqueResourceVsDetailedInfoMap.values());
		for (ResourceInfo resourceInfo : resourcesByCallDuration) {
			resourceInfo.countAverageDuration();
		}
		Collections.sort(resourcesByCallDuration, Collections.reverseOrder());
		
		//3. do smth with number resources by hours
		numberRequestCallsByHour = new ArrayList<>(numberRequestCallsByHourMap.values());
		Collections.sort(numberRequestCallsByHour);
	}

	public void parseLine(String line) {
		if (!LINE_IN_LOG.matcher(line).matches()) {
			return;
		}
		
		//1. split line and get
		String dateTime = LogParserUtils.getDateTimestamp(line);
		countRequest(dateTime);
		
		//2. getUniqueResource + duration of request
		String resourceName = LogParserUtils.getResourceName(line);	
		int durationCall = LogParserUtils.getDurationCall(line);
		
		//3. add Unique resource
		countResource(resourceName, durationCall);
	}
	
	public void printTopRequest(int number) {
		int counter = 0;
		while (counter < resourcesByCallDuration.size() && counter < number) {			
			ResourceInfo resourceInfo = resourcesByCallDuration.get(counter);
			System.out.println("Average duration (miliseconds): " + resourceInfo.getAvarageCallDuration() + ", resource name: '" + resourceInfo.getResourceName() +"'");
			
			counter++;
		}
	}	
	
	public void drawHistogram() {
		if (numberRequestCallsByHour.isEmpty()) {
			System.out.println(LanguageVariables.WARING_THERE_IS_NO_DATA);
			return;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append(getHeadOfHistogram());
		builder.append(getBodyOfHistogram());
		System.out.println(builder.toString());
	}
	
	private String getHeadOfHistogram() {
		StringBuilder builder = new StringBuilder("	      0|....");
		for (int i = 1; i < 9; i++) {
			builder.append("|....");			
		}
		builder.append("|.... " + maxNumberOfRequestByHour + "  requests\n");
		return builder.toString();
	}
	
	private String getBodyOfHistogram() {
		StringBuilder builder = new StringBuilder();
		for (NumberRequestCallsByHour byHour : numberRequestCallsByHour) {			
			builder.append("  " + byHour.getDateTime() + "|");
			
			int percantage =(int)((double) byHour.getRequestsCounter() / maxNumberOfRequestByHour *50);
			for (int i = 0; i < percantage; i++) {
				builder.append("-");
			}			
			builder.append(byHour.getRequestsCounter() + " requests\n");
		}		
		return builder.toString();
	}
	
	private void countRequest(String dateTime) {
		NumberRequestCallsByHour numberRequestCallsByHour = numberRequestCallsByHourMap.get(dateTime);		
		if(numberRequestCallsByHour == null) {
			numberRequestCallsByHourMap.put(dateTime, new NumberRequestCallsByHour(dateTime, 1));
		} else {
			numberRequestCallsByHour.addOneMoreCall();
			if (maxNumberOfRequestByHour < numberRequestCallsByHour.getRequestsCounter()) {
				maxNumberOfRequestByHour = numberRequestCallsByHour.getRequestsCounter();
			}
		}
	}
	
	private void countResource(String resourceName, int durationCall) {
		ResourceInfo resourceInfo = uniqueResourceVsDetailedInfoMap.get(resourceName);
		
		if (resourceInfo == null) {
			uniqueResourceVsDetailedInfoMap.put(resourceName, new ResourceInfo(resourceName, 1, durationCall));
		} else {
			resourceInfo.addOneMoreCall(durationCall);
		}
	}
	
}

