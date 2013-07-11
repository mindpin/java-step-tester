package com.junit4;

import java.io.IOException;


public class InputRuleMain {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String rule = "public void tasest123456() { RuleTest a = new RuleTest(); Assert.assertEquals(2,a.sum(1, 2));}";
		String input = "public int sum(int a, int b){ return a + b;}";
		
		InputRuleMethod.input(input,rule);
		InputRuleMethod.run();
	}
}