#!/bin/bash

kpid=$(cat /tmp/json-server.pid)
echo $kpid

[ -n "${kpid}" -a -d "/proc/${kpid}" ] && echo "process exists" || echo "process not exists"
