package test.com.bookmytrip;

import com.bookmytrip.formatter.JSONFormatter;

import junit.framework.TestCase;

public class TestJSONFormatter extends TestCase {
	
	public void testConvertStringToObject(){
		String str = "data:abcd";
		TestPojo pojo = (TestPojo)JSONFormatter.toObject(str, TestPojo.class);
		assertEquals("Invalid JSON is accepted", pojo,null);
		str = "{data1:'Srini',data2:23}";
		pojo = (TestPojo)JSONFormatter.toObject(str, TestPojo.class);
		assertEquals("Null returned for valida JSON",pojo != null,true);
		assertEquals("Invalida data found ",pojo.getData1(),"Srini");
		assertEquals("Invalida data found ",pojo.getData2(),23);
	}

	class TestPojo{
		private String data1;
		private int data2;
		public String getData1() {
			return data1;
		}
		public void setData1(String data1) {
			this.data1 = data1;
		}
		public int getData2() {
			return data2;
		}
		public void setData2(int data2) {
			this.data2 = data2;
		}
		
	}
}
