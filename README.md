# Perfect Numbers

Code challenge
___

Develop a small java program that checks if a number is perfect (as described here:
http://en.wikipedia.org/wiki/Perfect_number )

The program should contain two REST APIs to serve the following operations:

1. Check if a given number is perfect
2. Find all perfect numbers between a range (start-end)

Do not hardcode the known perfect numbers, instead write an algorithm that computes the perfect numbers. The program
should be written in Java 8 (Or later)
Technologies to use:
Use the following technologies:

* Embedded Jetty server
* Maven

Do include the following also:

* A few Junit tests to test the algorithm
* A few Integration tests (Fire the jetty container and publish the rest APIs on your test and use a client to send
  requests to the APIs).

---

### Run tests

```shell
mvn clean test
```

### Run perfect-numbers API

1) Build:

```shell
mvn clean install
```

2) Run:

```shell
cd <project_directory>/target
java -jar perfect-numbers-*-SNAPSHOT.jar
```