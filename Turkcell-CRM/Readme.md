# Spring Mikroservislerini Dockerize Etmek

Bu depo, Docker kullanarak containerize edilebilecek çeşitli Spring Boot mikroservislerini içermektedir. Her bir mikroservisi Docker kullanarak nasıl build edip çalıştırabileceğinizi öğrenmek için aşağıdaki adımları takip edin.

## Gereksinimler

- Makinenizde Docker kurulu olmalıdır
- Makinenizde Docker Compose kurulu olmalıdır

## Servisler

- Config Server
- Discovery Server
- Gateway Service
- Identity Service
- Customer Service
- ... ve diğer servisler

## Servislerin Build ve Çalıştırılması


### 1. Discovery Server

`discoveryServer` dizinine gidin, Docker imajını oluşturun ve container'ı çalıştırın.

```sh
cd discovery-server
```
- Bu dizinde `Dockerfile` dosyasını oluşturun
```Dockerfile
FROM maven:3.8.3-openjdk-17 as build

WORKDIR /app
COPY . /app/

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk

COPY --from=build /app/target/discoveryServer-0.0.1-SNAPSHOT.jar /app/discoveryServer.jar
EXPOSE 9000
CMD java -jar /app/discoveryServer.jar
```


### 2. Config Server


```sh
cd configServer
```
- Dockerfile oluştur
```Dockerfile
FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
COPY --from=build /app/target/configServer-0.0.1-SNAPSHOT.jar /app/configServer.jar

EXPOSE 8050

CMD ["java", "-jar", "/app/configServer.jar"]
```


### 3. Gateway Service

Diğer tüm servislerde benzer şekilde dockerize edilmiştir.
```sh
cd gatewayService
```
- Dockerfile oluştur
```Dockerfile
FROM maven:3.8.3-openjdk-17 as build

WORKDIR /app
COPY . /app/

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk

COPY --from=build /app/target/gatewayService-0.0.1-SNAPSHOT.jar /app/gatewayService.jar

EXPOSE 9002

CMD java -jar /app/gatewayService.jar
```

### Kök dizinde `docker-compose.yml` oluştur.

```
version: '3.8'
services:
  config-server:
    build: ./configServer
    ports:
      - "8050:8050"
    networks:
      - common

  discovery-server:
    build: ./discoveryServer
    ports:
      - "9000:9000"
    networks:
      - common

  gateway-service:
    build: ./gatewayService
    ports:
      - "9002:9002"
    depends_on:
      - discovery-server
      - config-server
    environment:
      - SPRING_CLOUD_CONFIG_PROFILE=prod
      - CONFIG_URL=http://config-server:8050
    restart: on-failure
    networks:
      - common

networks:
  common:
    driver: bridge

```


- `docker compose up -d` komutunu çalıştır ve artık servislerin ayakta.

- NOT: Konteynırlar çalıştıktan sonra biraz bekleyin eğer çalışmayan servisler varsa, `docker compose up --build -d` tekrar komutunu çalıştırın.

`Devamı gelicek . . .`

## Berat Hazer


