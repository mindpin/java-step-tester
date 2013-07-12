package com.socket;


public class JunitMain {
	public static void main(String[] args) {
		String  rule= "public void ab123() { RuleTest a = new RuleTest(); Assert.assertEquals(3,a.sum(1, 2));}";
		String  input= "public int sum(int a, int b){inat i; return a + b;}";
		
		SocketThead socketThead = new SocketThead(input, rule);
		socketThead.start();
	}
}
