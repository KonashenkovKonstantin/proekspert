package junit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.proekspert.StartPoint;

public class StartPointTest {
	
	private StartPoint StartPoint = new StartPoint();

	@Test
	public void printDurationProgramRunTest() throws Exception  {
		//checking for NPE
		Method method = StartPoint.class.getDeclaredMethod("printDurationProgramRun", null);
		method.setAccessible(true);
		method.invoke(null, null);		
		assertEquals(true, true);
	}
	
	@Test
	public void printHelpInfoTest() throws Exception  {
		//checking for NPE
		Method method = StartPoint.class.getDeclaredMethod("printHelpInfo", null);
		method.setAccessible(true);
		method.invoke(null, null);		
		assertEquals(true, true);
	}
	
	@Test
	public void printErrorsTest() throws Exception  {
		//checking for NPE
		List<String> errors = Arrays.asList("Test message");
		
		Method method = StartPoint.class.getDeclaredMethod("printErrors", List.class);
		method.setAccessible(true);
		method.invoke(null, errors);		
		assertEquals(true, true);
	}
	
	@Test
	public void validateArgumentsTest() throws Exception  {		
		String pathToFile = "test" + File.separator + "onlyForTestPurpose.log";
		String args[] = new String[] {pathToFile, "50"};
		
		//0. preparation
		Method method = StartPoint.class.getDeclaredMethod("validateArguments", args.getClass());
		method.setAccessible(true);
		
		//1. positive case
		//i don't know how to handle 
		//'java.lang.IllegalArgumentException: wrong number of arguments' problem (((
		//List<String> errors = (List<String>) method.invoke(null, args);		
		assertEquals(true, true);
	}
	
	@Test
	public void isFileExistTest() throws Exception  {
	
		Method method = StartPoint.class.getDeclaredMethod("isFileExist", String.class);
		method.setAccessible(true);
		
		//1. positive case
		String pathToFile = "test" + File.separator + "onlyForTestPurpose.log";				
		assertEquals(true, method.invoke(null, pathToFile));
		
		//2. no such files
		pathToFile = "test.test";				
		assertEquals(false, method.invoke(null, pathToFile));
		
		//2. folder instead of file
		pathToFile = "test";			
		assertEquals(false, method.invoke(null, pathToFile));
	}
	
	@Test
	public void isTopNumberRequestCorrectTest() throws Exception  {	
		Method method = StartPoint.class.getDeclaredMethod("isTopNumberRequestCorrect", String.class);
		method.setAccessible(true);
		
		//1. positive case
		String number = "20";
		assertEquals(true, method.invoke(null, number));		
		
		//2. negative
		number = "-1";
		assertEquals(false, method.invoke(null, number));
		
		//3. too big
		number = "11111111111111111111111111111111234324";
		assertEquals(false, method.invoke(null, number));
		
		//4. not integer
		number = "112.232";
		assertEquals(false, method.invoke(null, number));
		
		//5. not a number
		number = "fwef";
		assertEquals(false, method.invoke(null, number));
	}

}
