QBuddy API
==========
The QBuddy RESTful API ...

System Requirements:
--------------------
- Java JDK 1.8
- Maven 3.3.9 or higher

Building the example project:
-----------------------------

To download and unpack Swagger UI:

`mvn clean package`

When you run `mvn clean package` on the project, it will first download the Swagger-UI into the 'target' folder. 
Then, the 'dist' folder of the Swagger-UI project is copied into the /src/main/resources/swagger-ui folder of the project. 
When compiled, the swagger-ui folder will then appear in target/classes, and be accessible to your compiled and running fat JAR.

To build the fat JAR and run some tests:

`mvn clean install`

To run:

`java -jar target/qbuddy-api-1.0-SNAPSHOT.jar`

Hello World:

`http://localhost:8080/api/hello/World`

Swagger:

`http://localhost:8080/api/swagger.json`

Swagger UI:

`http://localhost:8080`
