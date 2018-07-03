package com.proekspert.content;

public class LanguageVariables {

	//info
	public static final String INFO_CLOSE_PROGRAMM_MESSAGE = "The programm terminated";
	public static final String INFO_DURATION_PROGRAMM_RUNNING = "Programm has been running for %d seconds";
	public static final String INFO_STARTED_PARSING_LOG_FILE = "Started parsing log file";
	public static final String INFO_FINISHED_PARSING_LOG_FILE = "Finished parsing log file";
	public static final String INFO_HELP = "Here is a list of possible commands:\n"
			+ "'-h' - print help info again,\n"
			+ "'-p' - print top p requests with highest avareage duration,\n"
			+ "'-d' - draw histogram of hourly number of requestsuration,\n"
			+ "'-s' - print out number of (milli)seconds this program run,\n"
			+ "'-q' - quit programm.\n";
	
	//warnings
	public static String WARING_STILL_PARSING = "The programm is still parsing log file";
	public static String WARING_THERE_IS_NO_DATA = "There is no data yet";
	
	//errors
	public static String ERROR_NO_ENOUGH_PARAMETERS = "Wrong parameters. You have to pass <file name> and <how much to print of resources with highest average request duration.>";
	public static String ERROR_WRONG_FILE_PATH = "Wrong parameter. File '%s' doesn't exist";
	public static String ERROR_WRONG_NUMBER_TO_PRINT = "Wrong parameter. Number '%s' isn't correct. It should be integer, positive and not more that 100";
	public static String ERROR_SMTH_WENT_WRONG = "Something went wrong";
	public static String ERROR_WRONG_COMMAND = "Wrong command. Use '-h' command to display all possible commands";
	
	
	
	
}

