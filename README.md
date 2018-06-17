# centerbooking

This is a Java API to book appointments to service centers.

## Environment Setup

1. Download JDK from Oracle: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
2. Install Maven fallowing the Maven Apache instructions: https://maven.apache.org/install.html
3. Set Environment Variables:
 i) JAVA_HOME: Pointing to JDK repository
 ii) M2_HOME: Pointing to Maven repository
 iii) Path: Add M2_HOME
4. Download Maven dependencies:
 i) On the terminal navigate to the project repository
 ii) Enter the command: mvn dependency:purge-local-repository
5. Create the database schema:
 i) Use the file centerbooking_create_schema.sql to create the database schema
6. Set the connection configuration in the file \src\main\resources\application.properties

## Running the Service

1. On the terminal navigate to the project repository
2. Enter the command: mvn spring-boot:run
3. The services should be running in http://localhost:8080
