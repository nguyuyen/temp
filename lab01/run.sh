#!/bin/bash

if [[ $1 = "compile" ]]; then
    rm -rf classes/*
    javac -d classes/ -cp "/home/nguyuyen01/kafka/libs/*" Consumer.java Producer.java SimpleKStream.java datatype/* custom/*
elif [[ $1 = "producer" ]]; then
    java -cp "/home/nguyuyen01/kafka/libs/*:classes/" Producer
elif [[ $1 = "consumer" ]]; then
    java -cp "/home/nguyuyen01/kafka/libs/*:classes/" Consumer $2
elif [[ $1 = "stream" ]]; then
    java -cp "/home/nguyuyen01/kafka/libs/*:classes/" SimpleKStream
fi