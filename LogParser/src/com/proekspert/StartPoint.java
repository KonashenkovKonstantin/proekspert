package com.proekspert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.proekspert.content.LanguageVariables;
import com.proekspert.services.LogParser;

public class StartPoint {
	private static final int MAXIMUM_NUMBER_OF_REQUESTS_TO_PRINT = 1000;
	
	private static final String COMMAND_HELP = "-h";
	private static final String COMMAND_PRINT_TOP_REQUESTS = "-p";
	private static final String COMMAND_DRAW_HISTOGRAM = "-d";
	private static final String COMMAND_PRINT_DURATION_PROGAMM_WORKING = "-s";
	private static final String COMMAND_QUIT = "-q";

	private static long startTime;
		
	public static void main(String args[]) {
		startTime = System.currentTimeMillis();
		
		//1. validate
		List<String> errors = validateArguments(args);
		if (errors != null && errors.size() > 0) {
			printErrors(errors);
			return;
		}
		
		//2. parse
		LogParser logParser = new LogParser(args[0]);
		Thread logParserThread = new Thread(logParser);
		logParserThread.start();
		
		
		//3. ready to handle commands from command line
		printHelpInfo();
		
		try (Scanner scan = new Scanner(System.in);) {
			while (true) {
				String command = scan.nextLine().trim();
				
				if (COMMAND_QUIT.equals(command)) {
					System.out.println(LanguageVariables.INFO_CLOSE_PROGRAMM_MESSAGE);
					return;
				} else if (COMMAND_HELP.equals(command)) {
					if (logParserThread.isAlive()) {
						System.out.println(LanguageVariables.WARING_STILL_PARSING);
					} else {
						printHelpInfo();
					}
										
				} else if (COMMAND_DRAW_HISTOGRAM.equals(command)) {
					logParser.drawHistogram();
				} else if (COMMAND_PRINT_TOP_REQUESTS.equals(command)) {
					logParser.printTopRequest(Integer.parseInt(args[1]));
				} else if (COMMAND_PRINT_DURATION_PROGAMM_WORKING.equals(command)) {
					printDurationProgramRun();
				} else {
					System.out.println(LanguageVariables.ERROR_WRONG_COMMAND);
				}
				
			}	
			
		} catch (Exception ee) {
			System.out.println(LanguageVariables.ERROR_SMTH_WENT_WRONG);
			ee.printStackTrace();
		}
	}	
				
	private static void printDurationProgramRun() {
		long  duration = System.currentTimeMillis() - startTime;
		System.out.println(String.format(LanguageVariables.INFO_DURATION_PROGRAMM_RUNNING, duration));
		
	}

	private static void printHelpInfo() {
		System.out.println(LanguageVariables.INFO_HELP);

	}

	private static void printErrors(List<String> errors) {
		for (String error : errors) {
			System.out.println(error);
		}
	}
	
	private static List<String> validateArguments(String args[]) {
		List<String> errors = new ArrayList<String>(); 
		if (args.length != 2) {
			errors.add(LanguageVariables.ERROR_NO_ENOUGH_PARAMETERS);
			return errors;
		}
				
		if (!isFileExist(args[0])) {
			errors.add(String.format(LanguageVariables.ERROR_WRONG_FILE_PATH, args[0]));
		}
		
		if (!isTopNumberRequestCorrect(args[1])) {
			errors.add(String.format(LanguageVariables.ERROR_WRONG_NUMBER_TO_PRINT, args[1]));
		}
		return errors;
	}
	
	private static boolean isFileExist(String filePathString) {
		File f = new File(filePathString);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		} else {
			return false;
		}
	}
	
	private static boolean isTopNumberRequestCorrect(String stringNumber) {
		try {
			int intNumber = Integer.parseInt(stringNumber);
			if (intNumber < 0 || intNumber > MAXIMUM_NUMBER_OF_REQUESTS_TO_PRINT) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
