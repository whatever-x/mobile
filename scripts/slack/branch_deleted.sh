#!/bin/bash

set -e

BRANCH_NAME="${{ github.event.ref }}"
ACTOR="${{ github.actor }}"
ACTOR_URL="https://github.com/${ACTOR}"

cat <<EOF > payload.json
{
  "blocks": [
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "🗑️ \\\`${BRANCH_NAME}\\\` 삭제"
      }
    },
    {
      "type": "context",
      "elements": [
        {
          "type": "mrkdwn",
          "text": "by <${ACTOR_URL}|${ACTOR}>"
        }
      ]
    }
  ]
}
EOF

curl -X POST -H 'Content-type: application/json' --data @payload.json $SLACK_WEBHOOK_URL

echo "✅ Slack notification sent for branch deleted."
