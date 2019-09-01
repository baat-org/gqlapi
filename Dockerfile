FROM openjdk:8-jre-alpine

# Dockerfile author / maintainer
MAINTAINER Sachin Goyal <sachin.goyal.se@gmail.com>

VOLUME /opt/gql_api

ADD build/libs/gql_api*.jar /opt/gql_api/gql_api.jar

ENTRYPOINT ["java","-jar","/opt/gql_api/gql_api.jar"]
