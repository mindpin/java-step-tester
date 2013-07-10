require 'socket'
require 'json'

class Client1
  # java -classpath jar名字.jar 包名.类名

  s = TCPSocket.new '127.0.0.1', 10001


  input = 'public void te2333232st133() { RuleTest a = new RuleTest(); Assert.assertEquals(3,a.sum(1, 2));}'
  rule = ' public int sum(int a, int b){ return a + b;}'

  h = { :input => input , :rule => rule }
  s.send(h.to_json.to_str + "\n", 0)
  s.flush

  while line = s.gets 
    puts line         # serveice : {error: null , success: success }
  end
  s.close

end