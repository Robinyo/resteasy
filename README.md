Getting started with RESTEasy
=============================
A step-by-step introduction to the RESTEasy framework.

System Requirements:
--------------------
- OpenJDK for Java 1.8
- Maven 3.3.9 or higher

OpenJDK for Java 1.8:
---------------------

Which JDKs do I have installed:

    update-java-alternatives -l

You should see output like:

    java-1.7.0-openjdk-amd64 1071 /usr/lib/jvm/java-1.7.0-openjdk-amd64

Install OpenJDK for Java 1.8:

    sudo add-apt-repository ppa:openjdk-r/ppa
    sudo apt update
    sudo apt install openjdk-8-jdk

Switch versions:

    sudo update-alternatives --config java
    sudo update-alternatives --config javac

Confirm the change:

    java -version
    javac -version

Building the project:
---------------------

    git clone https://github.com/Robinyo/resteasy.git