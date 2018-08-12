#!/bin/sh
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# Small shell script to show how to start the sample services.
#
# Adapt the following lines to your configuration
JSVC_HOME=/home/sunshiny/commons-daemon      # jsvc 파일 위치 디렉토리
JAVA_HOME=/usr/java/jdk1.6.0_45
PROGRAM=SimpleApplication
CLASSPATH=`pwd`/$PROGRAM.jar:`pwd`/commons-daemon-1.0.15.jar
 
case "$1" in
  start )
    shift
    $JSVC_HOME/jsvc \
        -home $JAVA_HOME \
        -cp $CLASSPATH \
        -nodetach \
        -errfile "&2" \
        -pidfile `pwd`/$PROGRAM.pid \
        @$PROGRAM \
        -start-method main \
        $*
    exit $?
    ;;
  stop )
    shift
    $JSVC_HOME/jsvc \
        -home $JAVA_HOME \
        -cp $CLASSPATH \
        -stop \
        -nodetach \
        -errfile "&2" \
        -pidfile `pwd`/$PROGRAM.pid \
        @$PROGRAM \
        -start-method main \
        $*
    exit $?
    ;;
    * )
    echo 'Usage SimpleApplication.sh start | stop'
    exit 1
    ;;
esac  