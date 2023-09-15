# log-2-hsqldb
Using Spring Boot Batch: Log to H2 file

## Summary
A custom-build server logs different events to a file. Every event has 2 entries in a log - one entry when the event was started and another when the event was finished. The entries in a log file have no specific order (it can occur that a specific event is logged before the event starts)
Every line in the file is a JSON object containing event data:
* id - the unique event identifier
* state - whether the event was started or finished (can have values "STARTED" or "FINISHED"
* timestamp - the timestamp of the event in milliseconds
Application Server logs also have the additional attributes:
* type - type of log
* host - hostname

Example

{"id":"scsmbstgra", "state":"STARTED", "type":"APPLICATION_LOG", "host":"12345", "timestamp":1491377495212}
{"id":"scsmbstgrb", "state":"STARTED", "timestamp":1491377495213}
{"id":"scsmbstgrc", "state":"FINISHED", "timestamp":1491377495218}
{"id":"scsmbstgra", "state":"FINISHED", "type":"APPLICATION_LOG", "host":"12345", "timestamp":1491377495217}
{"id":"scsmbstgrc", "state":"STARTED", "timestamp":1491377495210}
{"id":"scsmbstgrb", "state":"FINISHED", "timestamp":1491377495216}


## What the project does
Take the input file path as input argument.
Flag any long events that take longer than 4ms with a column in the database called "alert".
Write the found event details to file-based H2 in the working folder.
The application create a new table if necessary and enter the following values:
* Event id
* Event duration
* Type and Host if applicable
* "alert" true is applicable

## How to build
./gradlew clean build

### Reports
    build/reports/checkstyle/main.html
    build/reports/checkstyle/test.html
    build/reports/tests/test/index.html
    build/reports/jacoco/test/html/index.html

## How to execute
java -jar build/libs/log-2-hsqldb-1.0-SNAPSHOT.jar file=<path/file_name.log>

## Technologies used
- [Gradle 7.4](https://gradle.org/)
- [Java 17](https://openjdk.java.net/projects/jdk/17/)
- [Spring Boot 2.7](https://spring.io/projects/spring-boot)
- [Spring Batch](https://spring.io/projects/spring-batch)
- [Spring Data JPA](https://projects.spring.io/spring-data-jpa)
- [H2](http://www.h2database.com/)
- [Lombok 1.18](https://projectlombok.org/)
- [LogBack 1.2](https://logback.qos.ch/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [CheckStyle 8.44](https://checkstyle.sourceforge.io/)
- [Jacoco 0.8](https://www.jacoco.org/)
