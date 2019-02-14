#!/usr/bin/env bash

REPO_DIR=$(pwd)/$1
BUILD_DIR=$(pwd)/$2

echo $(pwd)
ls $(pwd)

ARTIFACT_NAME=$3

VERSION=$(pwd)/$4

ls $VERSION

VERSION_NUMBER=`cat $VERSION/version`
NUMBER=`cat $VERSION/number`

echo "VERSIONS"
echo $VERSION_NUMBER
echo $NUMBER

#NEW_NAME="${.jar/$VERSION.jar/}"

echo ${REPO_DIR}
echo ${BUILD_DIR}

mvn -f product-blacklist-repo/pom.xml clean package -X -e

if [ ! -f product-blacklist-repo/target/*.jar ]; then
    echo "No jar files found. Exiting with error"
    exit -1
fi

DEST_NAME="$ARTIFACT_NAME-$VERSION_NUMBER.jar"
echo $DEST_NAME

echo "copying files to output directory"
cp ${REPO_DIR}/target/*.jar ${BUILD_DIR}/$DEST_NAME
cp ${REPO_DIR}/concourse/assets/manifest*.yml ${BUILD_DIR}

echo "Final files"
ls ${BUILD_DIR}