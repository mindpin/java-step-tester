require 'socket'
require 'json'

class Client1
  # java -classpath jar名字.jar 包名.类名

  s = TCPSocket.new '127.0.0.1', 10001


  method = 'public void run(){ System.out.println("how are you"); }'
  scess = ' new Hello().run();'
  h = { :method => method , :scess => scess }
  s.send(h.to_json.to_str + "\n", 0)
  s.flush

  while line = s.gets # Read lines from socket
    puts line         # and print them
  end
  s.close

end