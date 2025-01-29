[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/yasmindias/loan-simulator/blob/master/README.md)

# Loan Simulator #
This project performs a loan simulation and returns the payment conditions, based on the requested value, interest rate and payment term.

## Run Locally
### Requirements
- [Java 17](https://jdk.java.net/archive/) **(OpenJDK)**
- [Gradle](https://gradle.org/install/)

### Run Project
Run the command ```./gradlew bootRun``` to start the project.

It will provide the url ``http://localhost:8080`` for the API calls, see [Swagger](http://localhost:8080/swagger-ui/index.html) for requests examples.

### Run Tests

#### Unit Tests
To run tests locally run the command ```./gradlew clean test```.

#### Integration Tests
To run integration tests locally run the command ```./gradlew clean integrationTest```.

#### Performance Tests
### Requirements
- [Docker](https://www.docker.com/products/docker-desktop/)

To run our load test we use the tool [K6](https://grafana.com/docs/k6/latest/), running in a Docker container.

Use the following commands to build and run the test.

```
docker build . -t dock-k6
docker run --add-host=host.docker.internal:host-gateway --rm dock-k6 run load_test.js
```

## Run on Container
### Requirements
- [Docker](https://www.docker.com/products/docker-desktop/)



## Documentation
- [Swagger](http://localhost:3000/swagger-ui/index.html)
-