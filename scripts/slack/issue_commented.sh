#!/bin/bash

set -e

ISSUE_NUMBER="${{ github.event.issue.number }}"
ISSUE_TITLE="${{ github.event.issue.title }}"
ISSUE_URL="${{ github.event.issue.html_url }}"
COMMENT_BODY="${{ github.event.comment.body }}"

COMMENT_AUTHOR="${{ github.event.comment.user.login }}"
COMMENT_AUTHOR_URL="https://github.com/${COMMENT_AUTHOR}"
COMMENT_AUTHOR_AVATAR="${{ github.event.comment.user.avatar_url }}"

# fallback
if [ -z "$COMMENT_BODY" ] || [ "$COMMENT_BODY" == "null" ]; then
  COMMENT_BODY="(코멘트가 없습니다)"
fi

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
        "text": "*[<#${ISSUE_NUMBER}|${ISSUE_TITLE}>]*\\n\\\`\\\`\\\`${COMMENT_BODY_ESCAPED}\\\`\\\`\\\`\\n<${ISSUE_URL}|이슈 바로가기>"
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

echo "✅ Slack notification sent for issue comment."
