FROM btgoose/jdk17
WORKDIR /app
COPY ./DR-service.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Xms256m","-Xmx512m","-jar","app.jar"]