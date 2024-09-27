FROM openjdk:17-jdk

WORKDIR /app

COPY target/ /

EXPOSE  8080

CMD ["java", "-jar"]