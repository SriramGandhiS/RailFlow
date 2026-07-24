# RailFlow Backend Dockerfile — 2026-07-24
# feat: add Dockerfile for Spring Boot backend production build
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/railflow-*.jar app.jar
EXPOSE 8080
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
