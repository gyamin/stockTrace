#!/bin/bash

baseDir=$(cd $(dirname $0); pwd)
shellName=$(basename $0)

cd $(dirname $(dirname ${baseDir}))
java -classpath stockTrace.jar:./lib/*:./ MainRunner
