require 'socket'
require 'json'

# 1.
# 某jar包有多个classw文件包含了main 函数,
# 需要在外部用java命令的bat文件来执行这个jar包的某个class文件的main函数.

# java -classpath jar名字.jar 包名.类名 

class Client

  client = TCPSocket.new '127.0.0.1', 10001

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
    }'

  input = ' public int sum(int a, int b){ int i; return a + b;}'


  h = {:input => input, :rule => rule}
  client.send(h.to_json.to_str + "\n", 0)
  client.flush

  response = client.gets
  client.send('bye' + "\n", 0)
  client.flush
  client.close

  puts response

end