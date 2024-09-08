FROM eclipse-temurin:21-jdk-jammy AS build
COPY . /sc-chat
WORKDIR /sc-chat
RUN ./mvnw clean install -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-jammy
RUN addgroup --system spring && adduser --system spring && adduser spring spring
USER spring:spring
COPY --from=build /sc-chat/target/chat-service.jar /sc-chat/app.jar
WORKDIR /sc-chat
ENTRYPOINT ["java", "-jar", "app.jar"]