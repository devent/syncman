#!/bin/bash
java -classpath "libs/Bootstrap.jar" \
    org.deventm.bootstrap.Bootstrap \
    -libs libs/ -mainclass org.deventm.syncman.cli.Start -- $*
