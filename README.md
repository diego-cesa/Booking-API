# centerbooking

This is an API to book appointments to service centers.

Project Setup

This solution was developed and tested in Windows platform.

Environment Setup

1. Download JDK from Oracle: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
2. Install Maven fallowing the Maven Apache instructions: https://maven.apache.org/install.html
3. Set Environment Variables:
a. -JAVA_HOME: Pointing to JDK repository
b. -M2_HOME: Pointing to Maven repository
c. -Path: Add M2_HOME
4. Download Maven dependencies:
d. -On the terminal navigate to the project repository
e. -Enter the command: mvn dependency:purge-local-repository
5. Create the database schema:
-Use the file centerbooking_create_schema.sql to create the database schema
6. Set the connection configuration in the file \src\main\resources\application.properties

Running the Service
1. On the terminal navigate to the project repository
2. Enter the command: mvn spring-boot:run
3. The services should be running in http://localhost:8080
