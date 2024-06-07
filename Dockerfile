FROM eclipse-temurin:21

COPY target/*.jar /veia-api.jar

CMD ["java", "-jar", "/veia-api.jar"]