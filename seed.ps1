# Run seed.sql against the local Calyx database.
# Usage: .\seed.ps1

$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$migrateUsersFile = Join-Path $projectRoot "migrate-users.sql"
$migrateMealFile = Join-Path $projectRoot "migrate-meal-model.sql"
$migrateProductCategoryFile = Join-Path $projectRoot "migrate-product-category.sql"
$seedFile = Join-Path $projectRoot "seed.sql"
$propsFile = Join-Path $projectRoot "src\main\resources\application.properties"

if (-not (Test-Path $seedFile)) {
    Write-Error "seed.sql not found at $seedFile"
}

$dbHost = "localhost"
$dbPort = "5432"
$dbName = "calyx_db"
$dbUser = "postgres"
$dbPassword = "1234"

if (Test-Path $propsFile) {
    Get-Content $propsFile | ForEach-Object {
        if ($_ -match '^\s*db\.url=jdbc:postgresql://([^:/]+):(\d+)/(.+)\s*$') {
            $dbHost = $Matches[1]
            $dbPort = $Matches[2]
            $dbName = $Matches[3]
        }
        elseif ($_ -match '^\s*db\.user=(.+)\s*$') { $dbUser = $Matches[1].Trim() }
        elseif ($_ -match '^\s*db\.password=(.+)\s*$') { $dbPassword = $Matches[1].Trim() }
    }
}

$psqlCandidates = @(
    "psql",
    "C:\Program Files\PostgreSQL\18\bin\psql.exe",
    "C:\Program Files\PostgreSQL\17\bin\psql.exe",
    "C:\Program Files\PostgreSQL\16\bin\psql.exe",
    "C:\Program Files\PostgreSQL\15\bin\psql.exe"
)

$psql = $null
foreach ($candidate in $psqlCandidates) {
    if ($candidate -eq "psql") {
        $cmd = Get-Command psql -ErrorAction SilentlyContinue
        if ($cmd) { $psql = $cmd.Source; break }
    }
    elseif (Test-Path $candidate) {
        $psql = $candidate
        break
    }
}

if (-not $psql) {
    Write-Error @"
psql.exe was not found.

Install PostgreSQL or add its bin folder to PATH, for example:
  C:\Program Files\PostgreSQL\18\bin

Or run manually:
  & 'C:\Program Files\PostgreSQL\18\bin\psql.exe' -h $dbHost -p $dbPort -U $dbUser -d $dbName -f `"$seedFile`"
"@
}

$env:PGPASSWORD = $dbPassword

Write-Host "Migrating users schema on $dbName ..."
& $psql -h $dbHost -p $dbPort -U $dbUser -d $dbName -f $migrateUsersFile
if ($LASTEXITCODE -ne 0) {
    Write-Error "migrate-users.sql failed with exit code $LASTEXITCODE"
}

Write-Host "Migrating meal model on $dbName ..."
& $psql -h $dbHost -p $dbPort -U $dbUser -d $dbName -f $migrateMealFile
if ($LASTEXITCODE -ne 0) {
    Write-Error "migrate-meal-model.sql failed with exit code $LASTEXITCODE"
}

Write-Host "Migrating product categories on $dbName ..."
& $psql -h $dbHost -p $dbPort -U $dbUser -d $dbName -f $migrateProductCategoryFile
if ($LASTEXITCODE -ne 0) {
    Write-Error "migrate-product-category.sql failed with exit code $LASTEXITCODE"
}

Write-Host "Seeding $dbName on ${dbHost}:${dbPort} using $psql ..."
& $psql -h $dbHost -p $dbPort -U $dbUser -d $dbName -f $seedFile

if ($LASTEXITCODE -ne 0) {
    Write-Error "seed.sql failed with exit code $LASTEXITCODE"
}

Write-Host "Done."
