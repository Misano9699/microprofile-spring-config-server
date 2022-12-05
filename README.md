# MicroProfile Spring Cloud Config Server Client

This project contains sample code for a MicroProfile Spring Cloud Config Client using the MicroProfile REST Client.

It contains the code for a Simple Spring Cloud Config Server and a MicroProfile Client.

## Spring Cloud Config Server

The Spring Cloud Config Server is a simple Spring Boot application with the Config Server enabled. 
For demonstration purposes the server contains a `native` config server with two profiles for the `microprofile-config` client application.

### Running the Config server 

Go to the `spring-config-server` directory and run the following command 

```commandline
mvn clean package spring-boot:run
```

## MicroProfile Config Client

MicroProfile Config Client is a simple MicroProfile application which uses the MicroProfile Config and REST Client API to load configuration properties from the Spring Cloud Config Server.
It uses Java 17 and the MicroProfile 5 and Jakarta EE 10 specifications. 
This application uses Payara Micro 6 as the MicroProfile implementation, but it should work with other MicroProfile implementations as well.   

### Running the Config server 

Go to the `microprofile-config-client` directory and run the following command

```commandline
mvn clean package payara-micro:start 
```

After the application has started, you can call the `greetings` endpoint and see the loaded `environment`

- http://localhost:8080/greetings/YourNameHere