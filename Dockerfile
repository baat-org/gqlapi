FROM adoptopenjdk/openjdk15:alpine-jre

# Dockerfile author / maintainer
MAINTAINER Sachin Goyal <sachin.goyal.se@gmail.com>

VOLUME /opt/gqlapi

ADD build/libs/gqlapi*.jar /opt/gqlapi/gqlapi.jar

ENTRYPOINT ["java","-jar","/opt/gqlapi/gqlapi.jar"]
