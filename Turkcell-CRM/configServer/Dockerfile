FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
COPY --from=build /app/target/configServer-0.0.1-SNAPSHOT.jar /app/configServer.jar

#EXPOSE 8050

CMD ["java", "-jar", "/app/configServer.jar"]
