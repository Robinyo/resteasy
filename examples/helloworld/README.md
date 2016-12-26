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

To build the WAR and run some tests:

    mvn clean install

To run Jetty:

    mvn jetty:run

Hello World:

    http://localhost:8080/hello/World

To stop Jetty:

    mvn jetty:stop
