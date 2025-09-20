#!/bin/bash

# Simple Ollama Installation and Setup Script
# This script helps install and manage Ollama natively

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

# Detect OS
detect_os() {
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        echo "linux"
    elif [[ "$OSTYPE" == "darwin"* ]]; then
        echo "macos"
    else
        echo "unknown"
    fi
}

# Install Ollama
install_ollama() {
    local os=$(detect_os)
    
    print_status "Installing Ollama..."
    
    if command -v ollama &> /dev/null; then
        print_warning "Ollama is already installed"
        return 0
    fi
    
    case $os in
        "linux")
            print_info "Installing Ollama for Linux..."
            curl -fsSL https://ollama.ai/install.sh | sh
            ;;
        "macos")
            print_info "Installing Ollama for macOS..."
            if command -v brew &> /dev/null; then
                brew install ollama
            else
                print_info "Downloading Ollama installer..."
                curl -fsSL https://ollama.ai/install.sh | sh
            fi
            ;;
        *)
            print_error "Unsupported operating system: $OSTYPE"
            print_info "Please visit https://ollama.ai/ for manual installation"
            exit 1
            ;;
    esac
    
    print_status "Ollama installed successfully!"
}

# Start Ollama server
start_server() {
    print_status "Starting Ollama server..."
    
    if pgrep -f "ollama serve" > /dev/null; then
        print_warning "Ollama server is already running"
        return 0
    fi
    
    # Start server in background
    nohup ollama serve > /tmp/ollama.log 2>&1 &
    local ollama_pid=$!
    
    print_info "Started Ollama server (PID: $ollama_pid)"
    
    # Wait for server to be ready
    local max_attempts=30
    local attempt=1
    
    while [ $attempt -le $max_attempts ]; do
        if curl -s http://localhost:11434/api/version &> /dev/null; then
            print_status "Ollama server is ready!"
            break
        fi
        
        print_info "Attempt $attempt/$max_attempts: Waiting for server..."
        sleep 2
        ((attempt++))
    done
    
    if [ $attempt -gt $max_attempts ]; then
        print_error "Ollama server failed to start within expected time"
        print_info "Check logs: tail -f /tmp/ollama.log"
        exit 1
    fi
    
    print_status "Ollama server started successfully at http://localhost:11434"
}

# Stop Ollama server
stop_server() {
    print_status "Stopping Ollama server..."
    
    local pids=$(pgrep -f "ollama serve" || true)
    
    if [ -z "$pids" ]; then
        print_warning "Ollama server is not running"
        return 0
    fi
    
    for pid in $pids; do
        kill $pid
        print_info "Stopped Ollama server (PID: $pid)"
    done
    
    print_status "Ollama server stopped"
}

# Check server status
check_status() {
    if curl -s http://localhost:11434/api/version &> /dev/null; then
        print_status "Ollama server is running at http://localhost:11434"
        
        # Get version info
        local version=$(curl -s http://localhost:11434/api/version 2>/dev/null | grep -o '"version":"[^"]*"' | cut -d'"' -f4 2>/dev/null || echo "unknown")
        print_info "Server version: $version"
        
        # List models
        print_info "Available models:"
        ollama list | tail -n +2 | awk '{print "  - " $1}' || echo "  No models found"
    else
        print_warning "Ollama server is not running"
        return 1
    fi
}

# Pull a model
pull_model() {
    local model_name="$1"
    
    if [ -z "$model_name" ]; then
        print_error "Please specify a model name"
        echo "Usage: $0 pull <model-name>"
        echo "Example: $0 pull llama3.2"
        exit 1
    fi
    
    print_status "Pulling model: $model_name"
    
    # Check if server is running
    if ! check_status &> /dev/null; then
        print_warning "Server not running, starting it first..."
        start_server
    fi
    
    ollama pull "$model_name"
    
    if [ $? -eq 0 ]; then
        print_status "Model $model_name pulled successfully"
    else
        print_error "Failed to pull model $model_name"
        exit 1
    fi
}

# Show help
show_help() {
    echo "Ollama Native Setup and Management Script"
    echo ""
    echo "Usage: $0 <command> [options]"
    echo ""
    echo "Commands:"
    echo "  install         Install Ollama (if not already installed)"
    echo "  start           Start the Ollama server"
    echo "  stop            Stop the Ollama server"
    echo "  restart         Restart the Ollama server"
    echo "  status          Check server status and list models"
    echo "  pull <model>    Pull a model from Ollama registry"
    echo "  setup           Complete setup (install + start + pull llama3.2)"
    echo "  help            Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 setup                   # Complete setup for first time"
    echo "  $0 start                   # Start the server"
    echo "  $0 pull llama3.2          # Pull llama3.2 model"
    echo "  $0 status                 # Check if server is running"
}

# Complete setup
complete_setup() {
    print_status "Running complete Ollama setup..."
    
    install_ollama
    start_server
    
    print_info "Pulling default model (llama3.2)..."
    pull_model "llama3.2"
    
    print_status "Setup complete! You can now run your Java examples."
    print_info "Try: mvn exec:java -Dexec.mainClass=\"com.ollama.api.examples.SimpleChatExample\""
}

# Main script logic
main() {
    case "${1:-help}" in
        install)
            install_ollama
            ;;
        start)
            start_server
            ;;
        stop)
            stop_server
            ;;
        restart)
            stop_server
            sleep 2
            start_server
            ;;
        status)
            check_status
            ;;
        pull)
            pull_model "$2"
            ;;
        setup)
            complete_setup
            ;;
        help|--help|-h)
            show_help
            ;;
        *)
            print_error "Unknown command: $1"
            echo ""
            show_help
            exit 1
            ;;
    esac
}

# Run main function with all arguments
main "$@"