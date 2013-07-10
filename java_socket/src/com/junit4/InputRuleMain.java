package com.junit4;

import java.io.IOException;


public class InputRuleMain {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String input = "public void tasest123() { RuleTest a = new RuleTest(); Assert.assertEquals(4,a.sum(1, 2));}";
		String rule = "public int sum(int a, int b){ return a + b;}";
		
		InputRuleMethod.input(input,rule);
		InputRuleMethod.run();
	}
}