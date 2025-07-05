FROM openjdk:21
EXPOSE 8080
ADD target/orderserviceapi.jar orderserviceapi.jar
ENTRYPOINT ["java","-jar","/orderserviceapi.jar"]