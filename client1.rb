require 'socket'
require 'json'
  
class Client1
  # java -classpath jar名字.jar 包名.类名

  s = TCPSocket.new '127.0.0.1', 10001


  rule = %'
      @Test
      public void test_1() {
        RuleTest a = new RuleTest();
        Assert.assertEquals(3,a.sum(1, 2));
      }

      @Test
      @TestDescription("sum(1, 2) -> 3")
      public void test_2() {
        RuleTest a = new RuleTest();
        Assert.assertEquals(5,a.sum(3,2));
      }      
    '

  input = ' public int sum(int a, int b){ int i; return a + b;}'


  h = { :input => input , :rule => rule }
  s.send(h.to_json.to_str + "\n", 0)
  s.flush

  while line = s.gets 
    puts line         # serveice : {error: null , success: success }
  end
  s.close

end