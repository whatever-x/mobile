#!/bin/bash

set -e

PR_NUMBER="${{ github.event.pull_request.number }}"
PR_TITLE="${{ github.event.pull_request.title }}"
PR_URL="${{ github.event.pull_request.html_url }}"

COMMENT_BODY="${{ github.event.comment.body }}"

COMMENT_AUTHOR="${{ github.event.comment.user.login }}"
COMMENT_AUTHOR_URL="https://github.com/${COMMENT_AUTHOR}"

if [ -z "$COMMENT_BODY" ] || [ "$COMMENT_BODY" == "null" ]; then
  COMMENT_BODY="(❌코멘트가 없습니다)"
fi

COMMENT_BODY_ESCAPED=$(printf "%s" "$COMMENT_BODY" | jq -Rs .)

cat <<EOF > payload.json
{
  "blocks": [
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "💬 PR 라인 코멘트\\n- *<${PR_URL}|${PR_TITLE}>* #${PR_NUMBER}\\n\\\`\\\`\\\`${COMMENT_BODY_ESCAPED}\\\`\\\`\\\`"
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

echo "✅ Slack notification sent for PR line comment."
