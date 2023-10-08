FROM amazoncorretto:17.0.8-alpine3.18 AS build
WORKDIR /app
COPY . /app/
RUN ./mvnw clean install

FROM amazoncorretto:17.0.8-alpine3.18 AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar /app/
EXPOSE 8080
CMD java -jar $(ls /app/*.jar)
