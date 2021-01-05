FROM adoptopenjdk:15-jre-hotspot

ADD target/komentarji-service.jar komentarji-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "komentarji-service.jar"]