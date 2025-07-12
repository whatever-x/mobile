#!/bin/bash

set -e

PR_NUMBER="${{ github.event.issue.number }}"
PR_TITLE="${{ github.event.issue.title }}"
PR_URL="https://github.com/${{ github.repository }}/pull/${PR_NUMBER}"

COMMENT_BODY="${{ github.event.comment.body }}"
COMMENT_AUTHOR="${{ github.event.comment.user.login }}"
COMMENT_AUTHOR_URL="https://github.com/${COMMENT_AUTHOR}"
COMMENT_AUTHOR_AVATAR="${{ github.event.comment.user.avatar_url }}"

if [ -z "$COMMENT_BODY" ] || [ "$COMMENT_BODY" == "null" ]; then
  COMMENT_BODY="(❌코멘트가 없습니다)"
fi

COMMENT_BODY_ESCAPED=$(printf "%s" "$COMMENT_BODY" | jq -Rs .)

cat <<EOF > payload.json
{
  "blocks": [
    {
      "type": "header",
      "text": {
        "type": "plain_text",
        "text": "💬 PR 코멘트",
        "emoji": true
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "*<${PR_URL}|${PR_TITLE}>* #${PR_NUMBER}\\n\\\`\\\`\\\`${COMMENT_BODY_ESCAPED}\\\`\\\`\\\`"
      },
      "accessory": {
        "type": "image",
        "image_url": "${COMMENT_AUTHOR_AVATAR}",
        "alt_text": "by ${COMMENT_AUTHOR}"
      }
    },
    {
      "type": "context",
      "elements": [
        {
          "type": "mrkdwn",
          "text": "by <${COMMENT_AUTHOR_URL}|${COMMENT_AUTHOR}>"
        }
      ]
    }
  ]
}
EOF

curl -X POST -H 'Content-type: application/json' --data @payload.json $SLACK_WEBHOOK_URL

echo "✅ Slack notification sent for PR comment."
