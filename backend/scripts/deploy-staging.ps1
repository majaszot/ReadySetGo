# Deploy to staging manually
# Usage: .\scripts\deploy-staging.ps1
# Requires RAILWAY_TOKEN, RAILWAY_STAGING_SERVICE_ID, RAILWAY_STAGING_ENVIRONMENT_ID in .env

Write-Host "Triggering staging deploy..." -ForegroundColor Cyan

$body = @{
    query = "mutation { deploymentCreate(input: { serviceId: `"$env:RAILWAY_STAGING_SERVICE_ID`", environmentId: `"$env:RAILWAY_STAGING_ENVIRONMENT_ID`" }) { id status } }"
} | ConvertTo-Json

Invoke-RestMethod `
    -Uri "https://backboard.railway.app/graphql/v2" `
    -Method POST `
    -Headers @{
        "Authorization" = "Bearer $env:RAILWAY_TOKEN"
        "Content-Type"  = "application/json"
    } `
    -Body $body

Write-Host "Done! Check Railway dashboard for deploy status." -ForegroundColor Green