FROM eclipse-temurin:25-jdk

WORKDIR /app
COPY . .

RUN apt-get update \
 && apt-get install -y curl \
 && rm -rf /var/lib/apt/lists/*

RUN chmod +x mvnw

EXPOSE 8080
ENTRYPOINT ["./mvnw", "spring-boot:run"]
