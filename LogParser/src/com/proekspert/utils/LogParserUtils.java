package com.proekspert.utils;

public class LogParserUtils {
	
	public static int getDurationCall(String line) {
		line = line.substring(line.lastIndexOf(" in ") + 4).trim();
		return Integer.parseInt(line);
	}	

	public static String getResourceName(String line) {
		int startIndex = line.indexOf("]")+1;		
		int endIndex = line.lastIndexOf(" in ");
		line = line.substring(startIndex, endIndex).trim();
		line = line.split(" ")[0].trim();				
		return getUniqueResourceName(line);
	}
	
	public static String getUniqueResourceName(String line) {
		int indexOfQuestionMark = line.indexOf("?");
		int indexOfAction = line.indexOf("action");		
		
		boolean noParametersInURL = (indexOfQuestionMark == -1);
		boolean haveParametersWithoutAction = (indexOfQuestionMark != -1 && indexOfAction == -1);
		boolean haveParametersWithAction = (indexOfQuestionMark != -1 || indexOfAction != -1 || indexOfAction > indexOfQuestionMark);
		
		if (noParametersInURL) {
			return line;
		} else if (haveParametersWithoutAction) {
			return line.substring(0, indexOfQuestionMark);
		} else if (haveParametersWithAction) {
			String uniqueResourceName = line.substring(0, indexOfQuestionMark + 1);
			int indexOfAmp = line.indexOf("&", indexOfAction);
			if (indexOfAmp == -1) {
				uniqueResourceName = uniqueResourceName + line.substring(indexOfAction);
			} else {
				uniqueResourceName = uniqueResourceName + line.substring(indexOfAction, indexOfAmp);
			}		
			return uniqueResourceName;
		} else {
			return line;
		}
	}

	
	public static String getDateTimestamp(String line) {
		return line.substring(0, 13);
	}

}
