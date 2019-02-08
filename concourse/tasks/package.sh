#!/usr/bin/env bash

REPO_DIR=$(pwd)/$1
BUILD_DIR=$(pwd)/$2

echo $(pwd)
echo ${REPO_DIR}
echo ${BUILD_DIR}

mvn -f product-blacklist-repo/pom.xml clean package -X -e -DskipTests

echo "list repo dir"
ls ${REPO_DIR}

echo "list repo target dir"
ls ${REPO_DIR}/target

echo "list build dir before copying"
ls -las ${BUILD_DIR}

echo "copying files to output directory"
cp ${REPO_DIR}/target/*.jar ${BUILD_DIR}
cp ${REPO_DIR}/concourse/assets/manifest.yml ${BUILD_DIR}

echo "list build dir after copying"
ls -las ${BUILD_DIR}