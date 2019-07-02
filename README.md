# customer-ETL
Code Challenge customer ETL problem using Spring Boot

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.bibek.customerETL.customerETLApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run -Dspring-boot.run.arguments=/src/Data1.csv,/src/Map1.csv
```
 This application takes 2 arguments, 1st one is the path of the data file and 2nd one is the path for the mapping file.
