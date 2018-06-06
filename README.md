# todolist-terminal<br />
TodoList Program<br />
<br />
#How to use<br />
<br />
<br />
1) Need Redis<br />
On Linux<br />
https://redis.io/topics/quickstart<br />
On window <br />
https://github.com/rgl/redis/downloads<br />
<br />
Start redis-server<br />
Default Redis running on port 6379<br />
<br />
2) Configure<br />
if redis-server running another port or another server just change "template.properties" <br /> 
  <br />
  redis.host = xxx.xxx.xxx (default 127.0.0.1)<br />
  redis.port = xxxx         (default 6379)<br />
<br />
<br />
3) Build<br />
<br />
  CD to root project <br />
  mvn install<br />
<br />
4) Run<br />
<br />
  CD /target<br />
<br />
  java -jar todoList.jar<br />
  <br />
  <br />
  Enjoy<br />
  
  





