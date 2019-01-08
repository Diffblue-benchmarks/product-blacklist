#!/usr/bin/env bash

mvn clean package -DskipTests
docker build --build-arg AWS_ACCESS_KEY=$AWS_ACCESS_KEY_ID --build-arg AWS_SECRET_KEY=$AWS_SECRET_ACCESS_KEY -f Docker/Dockerfile -t sainsburys/products-blacklist .
