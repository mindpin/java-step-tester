import java.io.IOException;

import org.junit.Assert;

import com.unit4.com.Practice;


public class InputRuleMain {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String input = "public void test1() { RuleTest a = new RuleTest(); Assert.assertEquals(3,a.sum(1, 2));}";
		String rule = "public int sum(int a, int b){ return a + b;}";
		InputRule.rule(rule);
		InputRule.input(input);
		InputRule.run();
	}
}
