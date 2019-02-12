#!/usr/bin/env bash

MESSAGE=$1
TEAMS_WEBHOOK_URL=$2

curl -H "Content-Type: application/json" -d "{\"text\": \"$MESSAGE\"}" "$TEAMS_WEBHOOK_URL"