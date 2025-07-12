#!/bin/bash

set -e

PR_NUMBER="${{ github.event.pull_request.number }}"
PR_TITLE="${{ github.event.pull_request.title }}"
PR_URL="${{ github.event.pull_request.html_url }}"
PR_HEAD_BRANCH="${{ github.event.pull_request.head.ref }}"
PR_BASE_BRANCH="${{ github.event.pull_request.base.ref }}"
MERGED_BY="${{ github.event.pull_request.merged_by.login }}"
MERGED_BY_URL="https://github.com/${MERGED_BY}"

cat <<EOF > payload.json
{
  "blocks": [
    {
      "type": "header",
      "text": {
        "type": "plain_text",
        "text": "🗑️ PR 병합",
        "emoji": true
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "*<${PR_URL}|${PR_TITLE}>* #${PR_NUMBER}\\n\\\`${PR_HEAD_BRANCH}\\\` → \\\`${PR_BASE_BRANCH}\\\`"
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "*<${MERGED_BY_URL}|${MERGED_BY}>* 님이 PR을 병합했습니다."
      }
    }
  ]
}
EOF

curl -X POST -H 'Content-type: application/json' --data @payload.json $SLACK_WEBHOOK_URL

echo "✅ Slack notification sent for PR merged."
