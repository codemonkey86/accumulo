#!/usr/bin/env bash

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin"/../../../../conf/accumulo-env.sh

if [ -z $ACCUMULO_HOME ] ; then
    echo "ACCUMULO_HOME is not set.  Please make sure it's set globally."
    exit 1
fi

RW_HOME=$ACCUMULO_HOME/test/system/randomwalk

echo 'killing random walkers'
pssh -h $RW_HOME/conf/walkers "pkill -f [r]andomwalk.Framework" < /dev/null