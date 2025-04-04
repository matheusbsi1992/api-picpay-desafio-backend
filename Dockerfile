LABEL authors="matheus"

FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

# Cria uma subpasta específica
RUN mkdir -p /opt/app

# Força permissões (caso necessário)
RUN chmod -R 777 /opt/app

# Cópia a pasta src para o caminho respectivo
COPY  src /opt/app/src

# Cópia a pasta de target
COPY target /opt/app/target

# Cópia o arquivo de pom
COPY pom.xml /opt/app

# Identifica a pasta de APP
WORKDIR /opt/app

# Compila o projeto e remove tests
RUN mvn clean install -DskipTests

# Instala o JDK 22
FROM eclipse-temurin:22-jdk-alpine

# Cópia arquivo .jar de alto execução
COPY --from=build /opt/app/target/*.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]