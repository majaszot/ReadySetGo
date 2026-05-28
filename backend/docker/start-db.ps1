# Local development only — staging and production use Railway PostgreSQL
$envFile = Resolve-Path "..\..\.env"
docker compose --env-file $envFile up -d