# todolist-terminal
TodoList Program

#How to use


1) Need Redis
On Linux
https://redis.io/topics/quickstart
On window 
https://github.com/rgl/redis/downloads

Start redis-server
Default Redis running on port 6379

2) Configure
if redis-server running another port or another server just change "template.properties"  
  redis.host = xxx.xxx.xxx (default 127.0.0.1)
  redis.port = xxxx         (default 6379)


3) Build

CD to root project 
  mvn install

4) Run

CD /target

  java -jar todoList.jar
  
  
Enjoy
  
  





