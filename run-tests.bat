@echo off
REM Script to run tests locally with Jenkins-like configuration (Windows)
REM This helps test the pipeline configuration before pushing to Jenkins

setlocal enabledelayedexpansion

REM Default values
set BROWSER=%1
if "%BROWSER%"=="" set BROWSER=chrome

set ENVIRONMENT=%2
if "%ENVIRONMENT%"=="" set ENVIRONMENT=dev

set HEADLESS=%3
if "%HEADLESS%"=="" set HEADLESS=false

set TAGS=%4
if "%TAGS%"=="" set TAGS=@smoke

set PARALLEL=%5
if "%PARALLEL%"=="" set PARALLEL=false

set THREADS=%6
if "%THREADS%"=="" set THREADS=1

REM Set base URL based on environment
if "%ENVIRONMENT%"=="dev" (
    set BASE_URL=https://dev1.mtomics.com
) else if "%ENVIRONMENT%"=="staging" (
    set BASE_URL=https://staging1.mtomics.com
) else if "%ENVIRONMENT%"=="prod" (
    set BASE_URL=https://mtomics.com
) else (
    set BASE_URL=https://dev1.mtomics.com
)

echo ==========================================
echo MTOmics Test Execution
echo ==========================================
echo Browser: %BROWSER%
echo Environment: %ENVIRONMENT%
echo Base URL: %BASE_URL%
echo Headless: %HEADLESS%
echo Tags: %TAGS%
echo Parallel: %PARALLEL%
echo Threads: %THREADS%
echo ==========================================

REM Clean previous build artifacts
echo Cleaning previous build artifacts...
if exist target rmdir /s /q target
if exist test-output rmdir /s /q test-output
if exist reports rmdir /s /q reports
if exist logs rmdir /s /q logs

mkdir reports\screenshots
mkdir logs

REM Build Maven command
set MVN_CMD=mvn clean test -Dbrowser=%BROWSER% -Dheadless=%HEADLESS% -Denvironment=%ENVIRONMENT% -Dbase.url=%BASE_URL% -Dcucumber.filter.tags="%TAGS%"

if "%PARALLEL%"=="true" (
    set MVN_CMD=%MVN_CMD% -Dparallel=methods -DthreadCount=%THREADS%
)

REM Execute tests
echo Executing tests...
call %MVN_CMD%

if %ERRORLEVEL% EQU 0 (
    echo ==========================================
    echo Tests completed successfully
    echo ==========================================
) else (
    echo ==========================================
    echo Tests failed
    echo ==========================================
)

REM Generate reports
echo Generating reports...
call mvn verify -DskipTests

REM Display report locations
echo ==========================================
echo Test Reports Generated
echo ==========================================
echo Cucumber HTML Report: target\cucumber-reports\cucumber.html
echo Extent Report: test-output\
echo Screenshots: reports\screenshots\
echo Logs: logs\
echo ==========================================

if "%1"=="--help" goto :help
if "%1"=="-h" goto :help
goto :end

:help
echo.
echo Usage: run-tests.bat [BROWSER] [ENVIRONMENT] [HEADLESS] [TAGS] [PARALLEL] [THREADS]
echo.
echo Parameters:
echo   BROWSER      - chrome, firefox, edge (default: chrome)
echo   ENVIRONMENT  - dev, staging, prod (default: dev)
echo   HEADLESS     - true, false (default: false)
echo   TAGS         - Cucumber tags (default: @smoke)
echo   PARALLEL     - true, false (default: false)
echo   THREADS      - Number of threads (default: 1)
echo.
echo Examples:
echo   run-tests.bat chrome dev false @smoke
echo   run-tests.bat firefox staging true @regression true 3
echo   run-tests.bat chrome dev false "@admin and @smoke"
echo.

:end
endlocal
