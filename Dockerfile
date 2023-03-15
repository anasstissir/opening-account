FROM openjdk:11
EXPOSE 8080
ADD build/libs/opening-0.0.1-SNAPSHOT.jar opening-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/opening-0.0.1-SNAPSHOT.jar"]
