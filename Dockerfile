FROM maven:3.8.4-openjdk-8 as maven
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package -DskipTests


FROM openjdk:17-jdk-slim
ARG JAR_FILE=bank-account-application-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY --from=maven /usr/src/app/bank-account-application/target/${JAR_FILE} /opt/app/
ENTRYPOINT ["java","-jar","bank-account-application-0.0.1-SNAPSHOT.jar"]