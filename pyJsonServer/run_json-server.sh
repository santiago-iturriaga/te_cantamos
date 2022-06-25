#!/bin/bash

EXEC_PATH=/archivos/mama_te_canta/neurodesarrollomusical/pyJsonServer

LOG_PATH=/archivos/mama_te_canta/log

#export PATH=/sbin:/usr/sbin:/bin:/usr/bin:/usr/local/sbin:/usr/local/bin
#umask 022
cd /
nohup setsid $EXEC_PATH/json-server.py $EXEC_PATH 2>$LOG_PATH/json-server.err 1>$LOG_PATH/json-server.out
#echo "$!" > /tmp/json-server.pid
