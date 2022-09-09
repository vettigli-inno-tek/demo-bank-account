# Demo Bank Account (Coding challenge)

The application allows you to manage the following operations on the account:
- Balance reading
- List of transactions
- Bank transfer

The operations are available as Rest-API.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)


## Build & Install locally

```shell
mvn clean install

```


## Running the application locally

```shell
cd bank-account-application
mvn spring-boot:run
```
or 
```shell
cd bank-account-application/target
java -jar .\bank-account-application-0.0.1-SNAPSHOT.jar
```


## URLs 

[Open Api  (http://localhost:8080/swagger-ui/index.html#)](http://localhost:8080/swagger-ui/index.html#)

Documentation 


[Console H2 (http://localhost:8080/h2-console/login.jsp)](http://localhost:8080/h2-console/login.jsp) 

Database Console 

## REST API

Base Url = `/bank/account{accountId}`

1. GET `/balance` 
2. GET `/transactions` 
3. POST `/payments/money-transfers`



## DOCKER

### Build
```shell
docker build -t demo-bank-account:latest .
```
### Run
```shell
docker run -d -p8080:8080 demo-bank-account:latest
```


## Postman  ( Testing )

It's present Posman collection for test SANDBOX and  API REST 

