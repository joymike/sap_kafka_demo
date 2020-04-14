#!/bin/bash
export KAFKA_HOME="/opt/kafka"
export KAFKA_HEAP_OPTS="-Xmx512M -Xms256M"
nohup sudo $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties > /dev/null 2>&1 &
sleep 2
nohup  $KAFKA_HOME/bin/kafka-server-start.sh  $KAFKA_HOME/config/server.properties > /dev/null 2>&1 &
sleep 2
