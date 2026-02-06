# ===== BUILD =====
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build


# copia TUDO do repo (pom raiz + módulos)
COPY . .

# compila só o service-discovery + dependências
RUN mvn -pl service-discovery -am clean package -DskipTests

# ===== RUNTIME =====
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY --from=build /build/service-discovery/target/service-discovery-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
