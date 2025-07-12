#!/bin/bash

set -e

PR_NUMBER="${{ github.event.pull_request.number }}"
PR_TITLE="${{ github.event.pull_request.title }}"
PR_URL="${{ github.event.pull_request.html_url }}"
PR_BODY="${{ github.event.pull_request.body }}"
PR_USER="${{ github.event.pull_request.user.login }}"
PR_USER_AVATAR="${{ github.event.pull_request.user.avatar_url }}"
PR_COMMITS="${{ github.event.pull_request.commits }}"
PR_HEAD_BRANCH="${{ github.event.pull_request.head.ref }}"
PR_BASE_BRANCH="${{ github.event.pull_request.base.ref }}"

if [ -z "$PR_BODY" ] || [ "$PR_BODY" == "null" ]; then
  PR_BODY="(❌본문이 없습니다)"
fi

PR_BODY_ESCAPED=$(printf "%s" "$PR_BODY" | jq -Rs .)

cat <<EOF > payload.json
{
  "blocks": [
    {
      "type": "header",
      "text": {
        "type": "plain_text",
        "text": "✅ PR 생성",
        "emoji": true
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "*<${PR_URL}|${PR_TITLE}>* #${PR_NUMBER}\\n\\\`${PR_HEAD_BRANCH}\\\` → \\\`${PR_BASE_BRANCH}\\\`\\n${PR_COMMITS}개의 커밋이 있습니다."
      },
      "accessory": {
        "type": "image",
        "image_url": "${PR_USER_AVATAR}",
        "alt_text": "by ${PR_USER}"
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "\\\`\\\`\\\`${PR_BODY_ESCAPED}\\\`\\\`\\\`"
      }
    },
    {
      "type": "context",
      "elements": [
        {
          "type": "mrkdwn",
          "text": "by ${PR_USER}"
        }
      ]
    }
  ]
}
EOF

curl -X POST -H 'Content-type: application/json' --data @payload.json $SLACK_WEBHOOK_URL

echo "✅ Slack notification sent for PR opened."
