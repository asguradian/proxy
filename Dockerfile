FROM base:v1
WORKDIR /service
EXPOSE 8080
COPY .  .
RUN mvn clean install
ENTRYPOINT ["java","-jar","target/service.jar"]


