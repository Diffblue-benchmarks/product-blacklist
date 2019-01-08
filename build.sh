#!/usr/bin/env bash

mvn clean package
docker build -f Docker/Dockerfile -t sainsburys/products-blacklist .
