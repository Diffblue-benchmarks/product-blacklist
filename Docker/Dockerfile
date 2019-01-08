FROM openjdk:8-jdk-alpine

VOLUME /tmp
COPY target/product-blacklist*.jar products-blacklist.jar
ARG AWS_ACCESS_KEY
ARG AWS_SECRET_KEY
ENV AWS_ACCESS_KEY_ID $AWS_ACCESS_KEY
ENV AWS_SECRET_ACCESS_KEY $AWS_SECRET_KEY

ENTRYPOINT ["/usr/bin/java", "-jar", "/products-blacklist.jar"]