#!/bin/bash

#LOG_PATH=/archivos/mama_te_canta
LOG_PATH=/tmp
python3 json-server.py &> $LOG_PATH/json-server.log &
echo "$!" > /tmp/json-server.pid
