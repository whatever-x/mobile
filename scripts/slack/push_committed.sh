#!/bin/bash

set -e

BRANCH_NAME="${BRANCH_NAME}"
PUSHER="${PUSHER}"
PUSHER_URL="https://github.com/${PUSHER}"

COMMIT_COUNT=$(jq '.commits | length' "$GITHUB_EVENT_PATH")

FIRST_COMMIT=$(jq -r '.commits[0] | "- \(.message) (`\(.author.name)`) "' "$GITHUB_EVENT_PATH")

if [ "$COMMIT_COUNT" -gt 1 ]; then
  OTHERS_COUNT=$((COMMIT_COUNT - 1))
  FIRST_COMMIT="${FIRST_COMMIT}(외 ${OTHERS_COUNT}개의 커밋이 포함되어있습니다.)"
fi

FIRST_COMMIT_ESCAPED=$(printf "%s" "$FIRST_COMMIT" | jq -Rs .)

cat <<EOF > payload.json
{
  "blocks": [
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "🚀 \\\`${BRANCH_NAME}\\\` 브랜치에 커밋이 푸시되었습니다."
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": ${FIRST_COMMIT_ESCAPED}
      }
    },
    {
      "type": "context",
      "elements": [
        {
          "type": "mrkdwn",
          "text": "by <${PUSHER_URL}|${PUSHER}>"
        }
      ]
    }
  ]
}
EOF

curl -X POST -H 'Content-type: application/json' --data @payload.json $SLACK_WEBHOOK_URL

echo "✅ Slack notification sent for push commits."
