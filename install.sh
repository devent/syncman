#!/bin/bash

VERSION=0.0.1-SNAPSHOT
IZPACK=~/IzPack
LAUNCH4J=~/launch4j

mvn package
pushd install/
rm libs/*
cp ../syncman-cli/target/syncman-cli-$VERSION.jar libs/syncman.jar
cp ../syncman-cli/target/dependency/*.jar libs/
$IZPACK/bin/compile install.xml -h /opt/izpack -b . -o syncman-install-v$VERSION.jar -k standard
$LAUNCH4J/launch4j $PWD/launch4j.xml
RES=$?
popd

echo "finish, $RES"