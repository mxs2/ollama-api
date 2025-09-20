@echo off
setlocal enabledelayedexpansion

REM Ollama Server Management Script for Windows
REM This script helps manage the Ollama server for the Java API client

REM Configuration
if "%OLLAMA_PORT%"=="" set OLLAMA_PORT=11434
if "%OLLAMA_HOST%"=="" set OLLAMA_HOST=localhost
set OLLAMA_URL=http://%OLLAMA_HOST%:%OLLAMA_PORT%

REM Function to print colored output
:print_status
echo [INFO] %~1
goto :eof

:print_success
echo [SUCCESS] %~1
goto :eof

:print_warning
echo [WARNING] %~1
goto :eof

:print_error
echo [ERROR] %~1
goto :eof

REM Function to check if Ollama is installed
:check_ollama_installed
where ollama >nul 2>nul
if errorlevel 1 (
    call :print_error "Ollama is not installed!"
    echo Please install Ollama first:
    echo   Windows: Download from https://ollama.ai/download
    exit /b 1
)
call :print_success "Ollama is installed"
goto :eof

REM Function to check if server is running
:check_server_running
curl -s "%OLLAMA_URL%/api/tags" >nul 2>nul
if errorlevel 1 (
    exit /b 1
) else (
    exit /b 0
)

REM Function to start the server
:start_server
call :print_status "Starting Ollama server..."

call :check_server_running
if not errorlevel 1 (
    call :print_warning "Ollama server is already running on %OLLAMA_URL%"
    goto :eof
)

REM Start Ollama in background
start /b ollama serve

REM Wait for server to start
call :print_status "Waiting for server to start..."
for /l %%i in (1,1,30) do (
    timeout /t 1 /nobreak >nul
    call :check_server_running
    if not errorlevel 1 (
        call :print_success "Ollama server started successfully on %OLLAMA_URL%"
        goto :eof
    )
)

call :print_error "Failed to start Ollama server"
exit /b 1

REM Function to stop the server
:stop_server
call :print_status "Stopping Ollama server..."

REM Kill Ollama processes
taskkill /f /im ollama.exe >nul 2>nul
if errorlevel 1 (
    call :print_warning "No Ollama server processes found"
) else (
    call :print_success "Ollama server stopped"
)
goto :eof

REM Function to restart the server
:restart_server
call :stop_server
timeout /t 2 /nobreak >nul
call :start_server
goto :eof

REM Function to check server status
:status_server
call :print_status "Checking Ollama server status..."

call :check_server_running
if errorlevel 1 (
    call :print_error "Ollama server is not running"
    exit /b 1
)

call :print_success "Ollama server is running on %OLLAMA_URL%"

echo.
echo Server Information:
echo ===================
echo URL: %OLLAMA_URL%
echo Port: %OLLAMA_PORT%

echo.
echo Available Models:
echo ==================
curl -s "%OLLAMA_URL%/api/tags" 2>nul | findstr "name" | findstr /v "null"
goto :eof

REM Function to pull a model
:pull_model
set model=%~1
if "%model%"=="" (
    call :print_error "Model name is required"
    echo Usage: %0 pull ^<model-name^>
    echo Example: %0 pull phi3
    exit /b 1
)

call :print_status "Pulling model: %model%"
ollama pull "%model%"
call :print_success "Model %model% pulled successfully"
goto :eof

REM Function to list recommended models
:list_models
echo Recommended Models for Java API Client:
echo ========================================
echo.
echo 🚀 Quick Start (Lightweight):
echo   phi3:mini    - 2.3GB - Fast, good for testing
echo   tinyllama    - 637MB - Very fast, basic capabilities
echo.
echo 💬 General Chat:
echo   phi3         - 3.8GB - Balanced performance
echo   llama3.2     - 2.0GB - Meta's latest model
echo   mistral      - 4.1GB - High quality responses
echo.
echo 💻 Code Generation:
echo   codellama    - 3.8GB - Code-focused model
echo   starcoder    - 2.4GB - Code completion
echo.
echo 👁️ Vision Models:
echo   llava        - 4.5GB - Text + image understanding
echo   llama3.2    - 829MB - Lightweight vision
echo.
echo 🧮 Math/Reasoning:
echo   dolphin-mixtral - 26GB - Advanced reasoning
echo.
echo To pull a model: %0 pull ^<model-name^>
goto :eof

REM Function to show help
:show_help
echo Ollama Server Management Script
echo ===============================
echo.
echo Usage: %0 ^<command^> [arguments]
echo.
echo Commands:
echo   start          Start the Ollama server
echo   stop           Stop the Ollama server
echo   restart        Restart the Ollama server
echo   status         Check server status and list models
echo   pull ^<model^>   Pull/download a model
echo   models         List recommended models
echo   help           Show this help message
echo.
echo Examples:
echo   %0 start
echo   %0 pull phi3
echo   %0 status
echo.
echo Environment Variables:
echo   OLLAMA_HOST    Server host (default: localhost)
echo   OLLAMA_PORT    Server port (default: 11434)
goto :eof

REM Main script logic
set command=%~1
if "%command%"=="" set command=help

if "%command%"=="start" (
    call :check_ollama_installed
    if not errorlevel 1 call :start_server
) else if "%command%"=="stop" (
    call :stop_server
) else if "%command%"=="restart" (
    call :check_ollama_installed
    if not errorlevel 1 call :restart_server
) else if "%command%"=="status" (
    call :status_server
) else if "%command%"=="pull" (
    call :check_ollama_installed
    if not errorlevel 1 call :pull_model "%~2"
) else if "%command%"=="models" (
    call :list_models
) else if "%command%"=="help" (
    call :show_help
) else if "%command%"=="--help" (
    call :show_help
) else if "%command%"=="-h" (
    call :show_help
) else (
    call :print_error "Unknown command: %command%"
    echo.
    call :show_help
    exit /b 1
)

endlocal