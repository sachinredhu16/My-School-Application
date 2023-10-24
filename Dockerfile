#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

#Information around who maintains the image
MAINTAINER sachin.com

# Add the application's jar to the image
COPY target/my-school-0.0.1-SNAPSHOT.jar my-school-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "my-school-0.0.1-SNAPSHOT.jar"]