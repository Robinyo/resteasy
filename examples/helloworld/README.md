Getting started with RESTEasy
=============================
A step-by-step introduction to the RESTEasy framework.

System Requirements:
-------------------------
- Java JDK 1.8
- Maven 3.3.9 or higher

Building the project:
-------------------------

1. ???

git clone ...

2. In the root directory
 
mvn clean install

This will build the WAR and run some tests.

cd ~/Eclipse/Neon/workspace/resteasy/examples/helloworld/
mvn org.eclipse.jetty:jetty-maven-plugin:9.0.6.v20130930:run

cd ~/Eclipse/Neon/workspace/resteasy/examples/helloworld/
mvn org.eclipse.jetty:jetty-maven-plugin:9.0.6.v20130930:stop

http://localhost:8080/hello/World