#!/bin/sh

TOKEN="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0YWxlaCIsImV4cCI6MTkxODQ2MTYwN30.GyMJCuJtB9nHEo7aa96dHmqrwVKQbbNJRC7FQkIKv8uCmlGTR7LvlBBE8ATKAwbLmGbKuVpD7GoOwgYu_wKM9w"

MODEL_URL="http://api.ugm.ug.tisserv.net/api/1.0/models?name=$1&token=${TOKEN}"

curl "${MODEL_URL}" > temp
echo "${MODEL_URL}"

promt

#cat temp
#
curl -X POST --data-binary "@temp" -H "Accepts: application/json" -H "Content-type: application/json" "http://api.ugm.ug.tisserv.net/api/1.0/models?token=${TOKEN}"
