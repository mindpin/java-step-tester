package com.socket;


public class JunitMain {
	public static void main(String[] args) {
		String input = "public void ab123() { RuleTest a = new RuleTest(); Assert.assertEquals(3,a.sum(1, 2));}";
		String rule = "public int sum(int a, int b){ return a + b;}";
		
		SocketThead socketThead = new SocketThead(input, rule);
		socketThead.start();
	}
}
