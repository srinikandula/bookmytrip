package com.samples;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;



class A{
	static String str = "A";
	private String c ="";
	protected int method(){
		return 0;
	}
}
class B extends A{
	static String str = "B";
	@Override
	protected int method() {
		return 0;
	}
}

public class Throw {

	public static void main(String[] args) {
		String str="<?xml version='1.0'><Name><Forename>John</Forename></name>";
		XMLStreamReader parser = new XMLStreamReader() {
			
			@Override
			public boolean standaloneSet() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void require(int arg0, String arg1, String arg2)
					throws XMLStreamException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int nextTag() throws XMLStreamException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int next() throws XMLStreamException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public boolean isWhiteSpace() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isStartElement() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isStandalone() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isEndElement() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isCharacters() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAttributeSpecified(int arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasText() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasNext() throws XMLStreamException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasName() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public String getVersion() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getTextStart() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getTextLength() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getTextCharacters(int arg0, char[] arg1, int arg2, int arg3)
					throws XMLStreamException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public char[] getTextCharacters() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getText() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getProperty(String arg0) throws IllegalArgumentException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getPrefix() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getPITarget() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getPIData() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getNamespaceURI(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getNamespaceURI(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getNamespaceURI() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getNamespacePrefix(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getNamespaceCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public NamespaceContext getNamespaceContext() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public QName getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Location getLocation() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getLocalName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getEventType() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getEncoding() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getElementText() throws XMLStreamException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getCharacterEncodingScheme() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAttributeValue(String arg0, String arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAttributeValue(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAttributeType(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAttributePrefix(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAttributeNamespace(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public QName getAttributeName(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAttributeLocalName(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getAttributeCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void close() throws XMLStreamException {
				// TODO Auto-generated method stub
				
			}
		};
		
		String s1 = "abc";
		String s2 = s1;
		String s3 = new  String("abc");
		String s4 = new String("abc");
		System.out.println(s3 == s4);
		System.out.println(s3.equals(s4));
		System.out.println(s1.equals(s2));
		int var1 = 1,var2 = 1;
		try{
		if(var1== var2){
			var1++;
		}
		}catch(Exception e){
			
		}finally{
			System.out.println(1);
		}
		System.out.println(2);
		System.out.println(256>>4);
		B b = new B();
		System.out.println(b.str);
		int []x[] =  new int[9][9];
		int a = 10;
		switch(a){
		case 1: 
			System.out.println("1");
		case 10:
			System.out.println("10");
		case 11:
			System.out.println("11");
				
			default:
			System.out.println("3");
		}
		
		int[] start = {11,21,31,41,51};
		int[] finish = {61,71,81,91,101,111,121,131};
		System.arraycopy(start, 0, finish, 0, start.length);
		System.out.println("Array");
		for(int i:finish)
		System.out.println(i);
	}
	public static int method(){
		try{
			{
				System.out.println("hey");
				{
					System.out.println("hey");
				}
			}
			
			throw new Exception();
		}catch(Exception e){
			return 10;
		}/*finally{
			return 3;
		}*/
		
		
	}
	final class Test{
		
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
}
