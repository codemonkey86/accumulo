#! /usr/bin/env bash
../../../bin/accumulo org.apache.accumulo.server.test.TestIngest -timestamp 3 -delete 1000000 0 1 &
../../../bin/accumulo org.apache.accumulo.server.test.TestIngest -timestamp 3 -delete 1000000 1000000 1 &
../../../bin/accumulo org.apache.accumulo.server.test.TestIngest -timestamp 3 -delete 1000000 2000000 1 &
../../../bin/accumulo org.apache.accumulo.server.test.TestIngest -timestamp 3 -delete 1000000 3000000 1 &
../../../bin/accumulo org.apache.accumulo.server.test.TestIngest -timestamp 3 -delete 1000000 4000000 1 &