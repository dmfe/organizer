#!/bin/bash

curl -H 'Content-type: application/json' \
     -X POST \
     -d '{
       "details": null
     }' \
     -s -v localhost:8080/api/tasks | jq; \
curl -H 'Content-type: application/json' \
     -H 'Accept-Language: ru' \
     -X POST \
     -d '{
       "details": null
     }' \
     -s -v localhost:8080/api/tasks | jq; \
curl -H 'Content-type: application/json' \
     -H 'Accept-Language: en' \
     -X POST \
     -d '{
       "details": null
     }' \
     -s -v localhost:8080/api/tasks | jq; \
curl -H 'Content-type: application/json' \
     -H 'Accept-Language: fr' \
     -X POST \
     -d '{
       "details": null
     }' \
     -s -v localhost:8080/api/tasks | jq
