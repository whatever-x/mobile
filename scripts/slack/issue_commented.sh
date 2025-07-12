#!/bin/bash

set -e

ISSUE_NUMBER="${{ github.event.issue.number }}"
ISSUE_TITLE="${{ github.event.issue.title }}"
ISSUE_URL="${{ github.event.issue.html_url }}"

COMMENT_BODY="${{ github.event.comment.body }}"
COMMENT_USER="${{ github.event.comment.user.login }}"
COMMENT_USER_AVATAR="${{ github.event.comment.user.avatar_url }}"

COMMENT_BODY_ESCAPED=$(printf "%s" "$COMMENT_BODY" | jq -Rs .)

cat <<EOF > payload.json
{
  "blocks": [
    {
      "type": "header",
      "text": {
        "type": "plain_text",
        "text": "💬 이슈 코멘트",
        "emoji": true
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "*[<${ISSUE_URL}|#${ISSUE_NUMBER}>]*\\n${ISSUE_TITLE}\\n\\\`\\\`\\\`${COMMENT_BODY_ESCAPED}\\\`\\\`\\\`"
      },
      "accessory": {
        "type": "image",
        "image_url": "${COMMENT_USER_AVATAR}",
        "alt_text": "by ${COMMENT_USER}"
      }
    },
    {
      "type": "context",
      "elements": [
        {
          "type": "mrkdwn",
          "text": "by ${COMMENT_USER}"
        }
      ]
    }
  ]
}
EOF

curl -X POST -H 'Content-type: application/json' --data @payload.json $SLACK_WEBHOOK_URL

echo "✅ Slack notification sent for issue comment."
