#!/usr/bin/env bash

REPO_DIR=$(pwd)/$1
BUILD_DIR=$(pwd)/$2

VERSION=$3

echo $(pwd)
echo ${REPO_DIR}
echo ${BUILD_DIR}

#echo "setting aws keys"
#export AWS_ACCESS_KEY_ID=$3
#export AWS_SECRET_ACCESS_KEY=$4
#
#echo ${AWS_ACCESS_KEY_ID}
#echo ${AWS_SECRET_ACCESS_KEY}

# TODO remove skipTests once the integration tests are sorted
mvn -f product-blacklist-repo/pom.xml clean package -X -e

if [[ ! -f target/*.jar ]]; then
    echo "No jar files found. Exiting with error"
    exit -1
fi

echo "list repo dir"
ls ${REPO_DIR}

echo "list repo target dir"
ls ${REPO_DIR}/target

echo "list build dir before copying"
ls -las ${BUILD_DIR}

echo "copying files to output directory"
cp ${REPO_DIR}/target/*.jar ${BUILD_DIR}
cp ${REPO_DIR}/concourse/assets/manifest*.yml ${BUILD_DIR}

echo "list build dir after copying"
ls -las ${BUILD_DIR}