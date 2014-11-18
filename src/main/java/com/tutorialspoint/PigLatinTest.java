package com.tutorialspoint;

import junit.framework.TestCase;

public class PigLatinTest extends TestCase {

	
	public void testConvertWord(){
		PigLatin latin = new PigLatin();
		assertEquals("Opstay",latin.convertWord("Stop"));
		assertEquals("arcay",latin.convertWord("car"));
			
	}
	public void testConvertWord1(){
		PigLatin latin = new PigLatin();
		assertEquals(false,latin.convertWord("Stop").equals("safdsf"));
	}
}
