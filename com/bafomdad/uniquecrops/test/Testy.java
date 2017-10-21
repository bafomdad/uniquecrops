package com.bafomdad.uniquecrops.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Testy {

	public static void main(String[] args) {
		
		try {
			init();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	private static class TestReflectionFactory {

		private Class testNest;
		private Field testHandler;
		
		private Class defaultTest;
		private Object instanceAnonymousTest;
		private Class anonymousTest;
		private Method registerTest;
		private Method defaultMethod;
		
		public boolean init() {
			try {
				testNest = Class.forName("com.bafomdad.uniquecrops.test.nest.TestNest");
				testHandler = testNest.getDeclaredField("testHandler");
				
				defaultTest = Class.forName("com.bafomdad.uniquecrops.test.nest.TestNest$DefaultTest");
				
				instanceAnonymousTest = testHandler.get(defaultTest);
				anonymousTest = instanceAnonymousTest.getClass();
				
				defaultMethod = defaultTest.getDeclaredMethod("registerSuperMethod", String.class, String[].class);
				registerTest = anonymousTest.getSuperclass().getDeclaredMethod("registerSuperMethod", String.class, String[].class);
				
				return true;
			} catch (Throwable t) {
				t.printStackTrace();
				return false;
			}
		}
	}
	
	public static void init() throws Throwable {
		
		TestReflectionFactory trf =  new TestReflectionFactory();
		if (!trf.init())
			throw new Exception("No valid method found to reflect.");

		/** 
		 * java.lang.IllegalAccessException: Class com.bafomdad.uniquecrops.test.Testy can not access a member of class com.bafomdad.uniquecrops.test.nest.TestNest$1 with modifiers ""
		 * :Thonk:
		*/
		trf.registerTest.setAccessible(true);
		trf.registerTest.invoke(trf.anonymousTest.newInstance(), "This", new String[] { "is", "a", "test"});
	}
}
