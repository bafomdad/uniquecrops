package com.bafomdad.uniquecrops.test.nest;

import scala.actors.threadpool.Arrays;

public class TestNest {
	
	public interface ITestHandler extends ITestRenderer {
		
		public void testy();
	}
	
	public interface ITestRenderer {
		
		public void renderTesty();
	}

	public abstract static class DefaultTest implements ITestHandler {
		
		public void registerSuperMethod(String output, String[] inputs) {
			
			System.out.println("Output: " + output + " / Inputs: " + Arrays.asList(inputs));
		}
	}
	
	public static DefaultTest testHandler = new DefaultTest() {

		@Override
		public void testy() {

			System.out.println("Testy");
		}

		@Override
		public void renderTesty() {

			System.out.println("Render testy");
		}
	};
}
