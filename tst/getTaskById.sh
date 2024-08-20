#!/bin/bash

taskId="${1:-bda909c0-ff56-48db-8661-681cb932fdd2}"

curl -H 'Content-type: application/json' \
     -X GET \
     -s -v localhost:8080/api/tasks/"$taskId" | jq
