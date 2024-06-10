FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
COPY --from=build /app/target/salesService-0.0.1-SNAPSHOT.jar /app/salesService.jar

#EXPOSE 9002

CMD ["java", "-jar", "/app/salesService.jar"]
