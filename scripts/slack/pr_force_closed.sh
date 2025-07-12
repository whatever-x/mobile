#!/bin/bash

set -e

PR_NUMBER="${{ github.event.pull_request.number }}"
PR_TITLE="${{ github.event.pull_request.title }}"
PR_URL="${{ github.event.pull_request.html_url }}"
CLOSED_BY="${{ github.actor }}"

cat <<EOF > payload.json
{
  "blocks": [
    {
      "type": "header",
      "text": {
        "type": "plain_text",
        "text": "🗑️ PR 강제 종료",
        "emoji": true
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "*<${PR_URL}|${PR_TITLE}>* #${PR_NUMBER}\\n\\n‼*${CLOSED_BY}* 님이 PR을 강제로 닫았습니다.‼"
      }
    }
  ]
}
EOF

curl -X POST -H 'Content-type: application/json' --data @payload.json $SLACK_WEBHOOK_URL

echo "🗑️ Slack notification sent for PR force closed."
