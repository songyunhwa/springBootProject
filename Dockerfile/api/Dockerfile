#!/bin/sh

FROM openjdk:8-jdk-alpine


RUN mkdir -p /project/logs
RUN mkdir -p /project/target

EXPOSE  9000

VOLUME [ "/project/logs", "/project/target" ]

ENTRYPOINT [ "java" , "-jar" , "/project/target/app.jar" ]
