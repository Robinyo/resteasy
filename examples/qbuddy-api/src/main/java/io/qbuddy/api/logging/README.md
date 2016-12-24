# Logging

## Logback configuration

```xml
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>jul-to-slf4j</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>jcl-over-slf4j</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>log4j-over-slf4j</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>${logback.version}</version>
    </dependency>
```

## Bridging legacy APIs

Often, some of the components you depend on rely on a logging API other than SLF4J. You may also assume that these components will not switch to SLF4J in the immediate future. To deal with such circumstances, SLF4J ships with several bridging modules which redirect calls made to **log4j**, **JCL** (Jakarta Commons Logging) and **java.util.logging** APIs to behave as if they were made to the SLF4J API instead.

## jul-to-slf4j bridge

The jul-to-slf4j module includes a java.util.logging (jul) handler, namely SLF4JBridgeHandler, which routes all incoming jul records to the SLF4j API. Please see <a href="http://robferguson.org/2016/12/11/resteasy-embedded-jetty-fat-jars-swagger-and-swagger-ui/" target="_blank">SLF4JBridgeHandler</a> javadocs for usage instructions.

## References
- http://www.slf4j.org/legacy.html
- http://logback.qos.ch/manual/configuration.html
- http://www.slf4j.org/api/org/slf4j/bridge/SLF4JBridgeHandler.html
