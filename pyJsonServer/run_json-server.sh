#!/bin/bash

EXEC_PATH=/archivos/mama_te_canta/neurodesarrollomusical/pyJsonServer
#EXEC_PATH=/tmp

LOG_PATH=/archivos/mama_te_canta
#LOG_PATH=/tmp

#export PATH=/sbin:/usr/sbin:/bin:/usr/bin:/usr/local/sbin:/usr/local/bin
#umask 022
cd /
nohup setsid $0 $EXEC_PATH/json-server.py 2>$LOG_PATH/json-server.err >$LOG_PATH/json-server.out &
echo "$!" > /tmp/json-server.pid
