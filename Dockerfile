# ---------- stage 1: dependências ----------
FROM maven:3.9-eclipse-temurin-21 AS deps

WORKDIR /build

# copiar apenas poms
COPY pom.xml .
COPY order/pom.xml order/
COPY delivery/pom.xml delivery/
COPY inventory/pom.xml inventory/
COPY payment/pom.xml payment/
COPY product/pom.xml product/
COPY gateway-service/pom.xml gateway-service/
COPY service-discovery/pom.xml service-discovery/
COPY nimbusmart-contracts/pom.xml nimbusmart-contracts/

RUN mvn -B -q -e -DskipTests dependency:go-offline


# ---------- stage 2: build ----------
FROM deps AS build

WORKDIR /build

COPY . .

ARG SERVICE

RUN mvn -pl ${SERVICE} -am -DskipTests package


# ---------- stage 3: runtime ----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

ARG SERVICE

COPY --from=build /build/${SERVICE}/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]