#!/bin/bash

curl -H 'Content-type: application/json' \
     -X GET \
     -s -v localhost:8080/api/tasks | jq
