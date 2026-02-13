# ===== BUILD =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build

# copia TUDO do repo (pom raiz + módulos)
COPY . .

# compila só o delivery + dependências
RUN mvn -pl delivery -am clean package -DskipTests

# ===== RUNTIME =====
FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /build/delivery/target/delivery-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
