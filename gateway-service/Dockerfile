# ===== BUILD =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build

# copia TUDO do repo (pom raiz + módulos)
COPY . .

# compila só o gateway-service + dependências
RUN mvn -pl gateway-service -am clean package -DskipTests

# ===== RUNTIME =====
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/gateway-service/target/gateway-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
