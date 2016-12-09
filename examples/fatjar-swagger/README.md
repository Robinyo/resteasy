Getting started with RESTEasy
=============================
A step-by-step introduction to the RESTEasy framework.

System Requirements:
--------------------
- Java JDK 1.8
- Maven 3.3.9 or higher

Building the example project:
-----------------------------

To build the fat JAR and run some tests:

`mvn clean install`

To run:

`java -jar target/fatjar-swagger-1.0-SNAPSHOT.jar`

Hello World:

`http://localhost:8080/hello/World`

Swagger:

`http://localhost:8080/swagger.json`

You should see output like:

`{"swagger":"2.0","info":{"version":"1.0.2"},"host":"localhost:8080","basePath":"/","schemes":["http"]}`

Swagger UI:

`mvn clean package`


When you run mvn clean package on your project, this will first download the Swagger-UI into target folder. Then, the dist folder of the Swagger-UI project is copied into the /src/main/resources/webapp folder of your main project. When compiled, the webapp folder will then appear in target/classes, and be accessible to your compiled and running server JAR.
