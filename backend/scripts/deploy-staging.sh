#!/bin/bash
# Deploy to staging manually
# Usage: ./scripts/deploy-staging.sh
# Requires RAILWAY_TOKEN, RAILWAY_STAGING_SERVICE_ID, RAILWAY_STAGING_ENVIRONMENT_ID in .env

echo "Triggering staging deploy..."

curl -X POST \
  -H "Authorization: Bearer $RAILWAY_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "query": "mutation { deploymentCreate(input: { serviceId: \"'$RAILWAY_STAGING_SERVICE_ID'\", environmentId: \"'$RAILWAY_STAGING_ENVIRONMENT_ID'\" }) { id status } }"
  }' \
  https://backboard.railway.app/graphql/v2

echo "\nDone! Check Railway dashboard for deploy status."