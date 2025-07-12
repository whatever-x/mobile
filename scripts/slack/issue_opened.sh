#!/bin/bash

set -e

ISSUE_NUMBER="${{ github.event.issue.number }}"
ISSUE_TITLE="${{ github.event.issue.title }}"
ISSUE_URL="${{ github.event.issue.html_url }}"
ISSUE_USER="${{ github.event.issue.user.login }}"
ISSUE_USER_AVATAR="${{ github.event.issue.user.avatar_url }}"
ISSUE_MILESTONE="${{ github.event.issue.milestone.title || '❌' }}"

cat <<EOF > payload.json
{
  "blocks": [
    {
      "type": "header",
      "text": {
        "type": "plain_text",
        "text": "✅ 이슈 생성",
        "emoji": true
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "*[<${ISSUE_URL}|#${ISSUE_NUMBER}>]*\\n${ISSUE_TITLE}\\n${ISSUE_MILESTONE}"
      },
      "accessory": {
        "type": "image",
        "image_url": "${ISSUE_USER_AVATAR}",
        "alt_text": "by ${ISSUE_USER}"
      }
    },
    {
      "type": "context",
      "elements": [
        {
          "type": "mrkdwn",
          "text": "by ${ISSUE_USER}"
        }
      ]
    }
  ]
}
EOF

curl -X POST -H 'Content-type: application/json' --data @payload.json $SLACK_WEBHOOK_URL

echo "✅ Slack notification sent for issue opened."