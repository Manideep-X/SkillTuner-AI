# ------------------------------
    # Build Stage
# ------------------------------
    
    FROM maven:3.9.11-eclipse-temurin-25-noble AS builder

    WORKDIR /app

    COPY pom.xml .
    COPY mvnw .
    COPY .mvn/ .mvn
    
    RUN chmod +x mvnw
    
    # If there are no dependency change then docker will reuse the existing dependency layer instead of downloading everything again
    RUN ./mvnw dependency:go-offline -B
    
    COPY src ./src

    RUN ./mvnw clean package -DskipTests
    
# ------------------------------
    # Run Stage
# ------------------------------

    FROM eclipse-temurin:25-jdk-noble

    COPY --from=builder /app/target/*.jar skilltuner-ai-v1.jar

    EXPOSE 8082

    # entrypoint is used instead of cmd, to avoid overriding the main command in CLI
    ENTRYPOINT [ "java", "-jar", "skilltuner-ai-v1.jar" ]