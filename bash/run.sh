#!/bin/bash

CUR_DIR=`pwd`
echo "Current directory: $CUR_DIR"

ls -l $CUR_DIR/target/*.jar

java -cp $CUR_DIR/target/*.jar com.noiprocs.App

