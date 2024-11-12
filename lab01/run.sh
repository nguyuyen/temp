#!/bin/bash

EXE_DIR="classes"
SRC=$(find . -name "*.java")

KAFKA="$KAFKA_HOME/libs/*"

compile() {
    mkdir -p "$EXE_DIR"
    javac -d "$EXE_DIR" -cp "$KAFKA" "$SRC"
}

producer() {
    java -cp "$EXE_DIR:$KAFKA" Producer
}

consumer() {
    java -cp "$EXE_DIR:$KAFKA" Consumer
}

clean() {
    rm -rf "$EXE_DIR"
}

case "$1" in
    compile)
        compile
        ;;
    producer)
        producer
        ;;
    clean)
        clean
        ;;
    *)
        echo "$SRC"
        ;;
esac