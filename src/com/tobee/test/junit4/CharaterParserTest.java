package com.tobee.test.junit4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.tobee.tests.CharaterParser;

public class CharaterParserTest {

	@Test
	public void testSkipSpecialHtmlChars() {
		CharaterParser parser = new CharaterParser();
		
		assertEquals("09호8697", parser.skipSpecialHtmlChars("09&nbsp;호&nbsp;8697"));
	}

}
