Getting started with RESTEasy
=============================
A step-by-step introduction to the RESTEasy framework.

System Requirements:
--------------------
- OpenJDK for Java 1.8
- Git
- Maven 3.3.9 or higher

Building the example project:
-----------------------------

To download and unpack Swagger UI:

    mvn clean package

When you run `mvn clean package` on the project, it will first download the Swagger-UI into the 'target' folder. 
Then, the 'dist' folder of the Swagger-UI project is copied into the /src/main/resources/swagger-ui folder of the project. 
When compiled, the swagger-ui folder will then appear in target/classes, and be accessible to your compiled and running fat JAR.

To build the fat JAR and run some tests:

    mvn clean install

To run:

    java -jar target/fatjar-swagger-guice-1.0-SNAPSHOT.jar

Hello World:

    http://localhost:8080/api/hello/World

Swagger:

    http://localhost:8080/api/swagger.json

Swagger UI:

    http://localhost:8080

References:
-----------

- https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-RESTEasy-2.X-Project-Setup-1.5
