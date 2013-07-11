 package temp; 
 import org.junit.Assert; 
 import org.junit.Test; 
 import org.junit.runner.RunWith; 
 import org.junit.runners.JUnit4; 
 import java.util.Iterator;
 import java.util.Set;
 import org.junit.runner.JUnitCore;
 import org.junit.runner.Result;
 @RunWith(JUnit4.class) 
 public class  InputTest{
 @Test 
public void tasest123456() { RuleTest a = new RuleTest(); Assert.assertEquals(2,a.sum(1, 2));}
 }
 class RuleTest{
public int sum(int a, int b){ return a + b;}
 }