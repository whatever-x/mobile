#!/bin/bash

set -e

GITHUB_TOKEN="${{ secrets.GITHUB_TOKEN }}"
REPO="${{ github.repository }}"
ISSUE_NUMBER="${{ github.event.issue.number }}"
ISSUE_TITLE="${{ github.event.issue.title }}"
ISSUE_URL="${{ github.event.issue.html_url }}"
ISSUE_USER="${{ github.event.issue.user.login }}"
ISSUE_USER_AVATAR="${{ github.event.issue.user.avatar_url }}"

LINKED_PRS=$(curl -s \
  -H "Authorization: Bearer ${GITHUB_TOKEN}" \
  -H "Accept: application/vnd.github.v3+json" \
  "https://api.github.com/repos/${REPO}/issues/${ISSUE_NUMBER}/events" \
  | jq -r '
    .[]
    | select(.event == "cross-referenced")
    | select(.source.issue.pull_request)
    | "- [#\(.source.issue.number)] \(.source.issue.title)"'
)

if [ -z "$LINKED_PRS" ]; then
  LINKED_PRS="연결된 PR이 없습니다!"
fi

LINKED_PRS_ESCAPED=$(printf "%s" "$LINKED_PRS" | jq -Rs .)

cat <<EOF > payload.json
{
  "blocks": [
    {
      "type": "header",
      "text": {
        "type": "plain_text",
        "text": "🗑️ 이슈 종료",
        "emoji": true
      }
    },
    {
      "type": "section",
      "text": {
        "type": "mrkdwn",
        "text": "*[<${ISSUE_URL}|#${ISSUE_NUMBER}>]*\\n${ISSUE_TITLE}\\n\\n${LINKED_PRS_ESCAPED}"
      },
      "accessory": {
        "type": "image",
        "image_url": "${ISSUE_USER_AVATAR}",
        "alt_text": "by ${ISSUE_USER}"
      }
    }
  ]
}
EOF

curl -X POST -H 'Content-type: application/json' --data @payload.json $SLACK_WEBHOOK_URL

echo "✅ Slack notification sent for issue closed."
