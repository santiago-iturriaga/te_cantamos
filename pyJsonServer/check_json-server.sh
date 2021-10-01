#!/bin/bash

kpid=$(cat /tmp/json-server.pid)
#echo $kpid

#[ -n "${kpid}" -a -d "/proc/${kpid}" ] && echo "process exists" || echo "process not exists"

[ -n "${kpid}" -a -d "/proc/${kpid}" ] || curl -s --form-string "token=a4eqquv5vnpu86kxe5jv668vwoa77s" --form-string "user=ut7o7uwy569ob7p5dvjqmya54rd5kk" --form-string "message=Error con mama_te_canta server!" --form-string "priority=1" https://api.pushover.net/1/messages.jsonZ
