 package temp; 
 import org.junit.Assert; 
 import org.junit.Test; 
 import org.junit.runner.RunWith; 
 import org.junit.runners.JUnit4; 
 @RunWith(JUnit4.class) 
 public class  InputTest{
 @Test 
public void test1() { RuleTest a = new RuleTest(); Assert.assertEquals(3,a.sum(1, 2));}
 }