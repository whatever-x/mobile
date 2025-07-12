#!/bin/bash

set -e

PR_NUMBER="${{ github.event.pull_request.number }}"
PR_TITLE="${{ github.event.pull_request.title }}"
PR_URL="${{ github.event.pull_request.html_url }}"
PR_BRANCH="${{ github.event.pull_request.head.ref }}"

REVIEWER="${{ github.event.review.user.login }}"
REVIEWER_URL="https://github.com/${REVIEWER}"
REVIEWER_AVATAR="${{ github.event.review.user.avatar_url }}"

REVIEW_BODY="${{ github.event.review.body }}"

if [ -z "$REVIEW_BODY" ] || [ "$REVIEW_BODY" == "null" ]; then
  REVIEW_BODY="(❌코멘트가 없습니다)"
fi

REVIEW_BODY_ESCAPED=$(printf "%s" "$REVIEW_BODY" | jq -Rs .)

HEADER="${REVIEW_HEADER:-💬 PR 리뷰 코멘트}"

cat <<EOF > payload.json
{
  "blocks": [
    {
      "type": "header",
      "text": {
        "type": "plain_text",
        "text": "${HEADER}",
        "emoji": true
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "- *<${PR_URL}|${PR_TITLE}>* #${PR_NUMBER}\\n- \\\`${PR_BRANCH}\\\`\\n- *<${REVIEWER_URL}|${REVIEWER}>* 님의 리뷰 코멘트\\n\\\`\\\`\\\`${REVIEW_BODY_ESCAPED}\\\`\\\`\\\`"
      },
      "accessory": {
        "type": "image",
        "image_url": "${REVIEWER_AVATAR}",
        "alt_text": "by ${REVIEWER}"
      }
    }
  ]
}
EOF

curl -X POST -H 'Content-type: application/json' --data @payload.json $SLACK_WEBHOOK_URL

echo "✅ Slack notification sent for PR review submitted: ${HEADER}"
