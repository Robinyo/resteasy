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
