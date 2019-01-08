FROM openjdk:8-jdk-alpine

VOLUME /tmp
COPY target/product-blacklist*.jar products-blacklist.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/products-blacklist.jar"]