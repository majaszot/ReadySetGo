# ── Build stage ──────────────────────────────────────────────
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle shadowJar --no-daemon

# ── Run stage ────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/backend.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -DPORT=${PORT:-8080} -jar backend.jar"]