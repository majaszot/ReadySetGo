# Deploy to staging manually
# Usage: .\scripts\deploy-staging.ps1
# Requires RAILWAY_TOKEN, RAILWAY_STAGING_SERVICE_ID, RAILWAY_STAGING_ENVIRONMENT_ID in .env

# Load .env from repo root
Get-Content "$PSScriptRoot\..\..\\.env" | ForEach-Object {
    if ($_ -match '^\s*([^#][^=]*?)\s*=\s*(.*)\s*$') {
        [System.Environment]::SetEnvironmentVariable($matches[1], $matches[2])
    }
}

# Script itself
Write-Host "Triggering staging deploy..." -ForegroundColor Cyan

$body = @{
    query = "mutation { serviceInstanceRedeploy(environmentId: `"$env:RAILWAY_STAGING_ENVIRONMENT_ID`", serviceId: `"$env:RAILWAY_STAGING_SERVICE_ID`") }"
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