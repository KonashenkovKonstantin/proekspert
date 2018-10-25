package junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.proekspert.utils.LogParserUtils;

public class LogParserUtilsTest {


	@Test
	public void getDurationCallTest() {
		String test1 = "2015-08-19 00:00:01,049 (http--0.0.0.0-28080-405) [] /checkSession.do in 187";
		String test2 = "2015-08-19 00:00:01,963 (http--0.0.0.0-28080-245) [] /checkSession.do in 113";
		String test3 = "2015-08-19 00:00:02,814 (http--0.0.0.0-28080-245) [CUST:CUS5T27233] /substypechange.do?msisdn=300501633574 in 17";
		String test4 = "2015-08-19 00:00:03,260 (http--0.0.0.0-28080-245) [CUST:CUS5T27233] /mainContent.do?action=TOOLS&contentId=main_tools in 5";
		
		assertEquals(187, LogParserUtils.getDurationCall(test1));
		assertEquals(113, LogParserUtils.getDurationCall(test2));
		assertEquals(17, LogParserUtils.getDurationCall(test3));
		assertEquals(5, LogParserUtils.getDurationCall(test4));
	}
	
	@Test
	public void getResourceNameTest() {
		//test only correct splitting. 
		//All aggregation and removing trash from URL will be tested in  getUniqueResourceNameTest
		String test1 = "2015-08-19 00:04:45,212 (http--0.0.0.0-28080-405) [] updateSubscriptionFromBackend 300445599231 in 203";
		String test2 = "2015-08-19 00:04:45,212 (http--0.0.0.0-28080-405) [] updateSubscriptionFromBackend 300445599231 in in in 203";
		
		assertEquals("updateSubscriptionFromBackend", LogParserUtils.getResourceName(test1));
		assertEquals("updateSubscriptionFromBackend", LogParserUtils.getResourceName(test2));
	}
	
	@Test
	public void getUniqueResourceNameTest() {
		//1. method name instead of URL
		String testUrl = "updateSubscriptionFromBackend";
		String expectedResult = "updateSubscriptionFromBackend";
		assertEquals(expectedResult, LogParserUtils.getUniqueResourceName(testUrl));
		
		//2. url with bunch of parameters, but without 'action' parameter
		testUrl = "/mobilityServices.do?msisdn=300445644148&actio=SERVICES&tab=inuse";
		expectedResult = "/mobilityServices.do";
		assertEquals(expectedResult, LogParserUtils.getUniqueResourceName(testUrl));
		
		//3. url with bunch of parameters  with action parameter
		// 'action' right after '?'
		testUrl = "/mobilityServices.do?action=SERVICES&msisdn=300445644148&contentId=main_subscription";
		expectedResult = "/mobilityServices.do?action=SERVICES";
		assertEquals(expectedResult, LogParserUtils.getUniqueResourceName(testUrl));
		
		// 'action' in the middle of bunch parameters
		testUrl = "/mobilityServices.do?msisdn=300445644148&action=SERVICES&contentId=main_subscription";
		assertEquals(expectedResult, LogParserUtils.getUniqueResourceName(testUrl));
		
		// 'action' the last parameter
		testUrl = "/mobilityServices.do?msisdn=300445644148&contentId=main_subscription&action=SERVICES";
		assertEquals(expectedResult, LogParserUtils.getUniqueResourceName(testUrl));
	}
	
	@Test
	public void getDateTimestampTest() {
		String test1 = "2015-08-19 00:00:01,049 (http--0.0.0.0-28080-405) [] /checkSession.do in 187";
		String test2 = "2015-08-19 00:00:01,963 (http--0.0.0.0-28080-245) [] /checkSession.do in 113";
		
		assertEquals("2015-08-19 00", LogParserUtils.getDateAndHour(test1));
		assertEquals("2015-08-19 00", LogParserUtils.getDateAndHour(test2));
	}

}
