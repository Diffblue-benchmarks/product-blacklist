#!/usr/bin/env bash

REPO_DIR=$(pwd)/$1
BUILD_DIR=$(pwd)/$2

echo $(pwd)
echo ${REPO_DIR}
echo ${BUILD_DIR}

mvn -f product-blacklist-repo/pom.xml clean package -X -e -DskipTests