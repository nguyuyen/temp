#!/bin/bash

if [[ $1 = "compile" ]]; then
    rm -rf classes/*
    javac -d classes/ -cp "/home/trieu/kafka/libs/*" Consumer.java Producer.java datatype/* custom/*
elif [[ $1 = "producer" ]]; then
    java -cp "/home/trieu/kafka/libs/*:classes/" Producer
elif [[ $1 = "consumer" ]]; then
    java -cp "/home/trieu/kafka/libs/*:classes/" Consumer $2
fi