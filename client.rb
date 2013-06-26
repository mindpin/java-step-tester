require 'socket'

# 1.
# 某jar包有多个classw文件包含了main 函数,
# 需要在外部用java命令的bat文件来执行这个jar包的某个class文件的main函数.

# java -classpath jar名字.jar 包名.类名 

class Client

  s = TCPSocket.new '127.0.0.1', 10000

  1.upto(10) do |n|
    s.send("hello #{n}\n",0)
    s.flush
  end

  while line = s.gets # Read lines from socket
    puts line         # and print them
  end
  s.close  

end