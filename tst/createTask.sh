#!/bin/bash

curl -H 'Content-type: application/json' \
     -X POST \
     -d '{
       "details": "Task three"
     }' \
     -s -v localhost:8080/api/tasks | jq