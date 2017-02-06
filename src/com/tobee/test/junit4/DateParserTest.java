package com.tobee.test.junit4;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tobee.tests.DateParser;

public class DateParserTest {

	@Test
	public void testGetCurrentDate() {
		//fail("Not yet implemented");
		
		assertEquals("", new DateParser().getCurrentDate());
	}

}
