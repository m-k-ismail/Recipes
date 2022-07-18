FROM openjdk:11
EXPOSE 8080
COPY target/*.war /recipe-application.war
ENTRYPOINT ["java", "-jar", "recipe-application.war"]