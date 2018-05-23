FROM maven:latest as build
WORKDIR /usr/src/

COPY pom.xml /tmp/pom.xml
RUN mvn -B -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve

COPY . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM java:8-jdk-alpine

WORKDIR /app
COPY --from=build /usr/src/target/poc.drools-0.0.1-SNAPSHOT.jar .
EXPOSE 5005
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005","-jar", "/app/poc.drools-0.0.1-SNAPSHOT.jar"]