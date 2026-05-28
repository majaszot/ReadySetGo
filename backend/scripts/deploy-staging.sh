#!/bin/bash
# Deploy to staging manually
# Usage: ./scripts/deploy-staging.sh
# Requires RAILWAY_TOKEN, RAILWAY_STAGING_SERVICE_ID, RAILWAY_STAGING_ENVIRONMENT_ID in .env

# Load .env from repo root
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ENV_FILE="$SCRIPT_DIR/../../.env"

if [ -f "$ENV_FILE" ]; then
    export $(grep -v '^#' "$ENV_FILE" | xargs)
fi

echo "Triggering staging deploy..."

curl -X POST \
  -H "Authorization: Bearer $RAILWAY_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"query\": \"mutation { serviceInstanceRedeploy(environmentId: \\\"$RAILWAY_STAGING_ENVIRONMENT_ID\\\", serviceId: \\\"$RAILWAY_STAGING_SERVICE_ID\\\") }\"}" \
  https://backboard.railway.app/graphql/v2

echo "\nDone! Check Railway dashboard for deploy status."