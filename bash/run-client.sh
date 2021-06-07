#!/bin/bash

CUR_DIR=`pwd`
echo "Current directory: $CUR_DIR"

ls -l $CUR_DIR/target/*-with-dependencies.jar

java -cp $CUR_DIR/target/*-with-dependencies.jar com.noiprocs.App pc noiprocs client localhost 8080

