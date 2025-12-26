# Load environment variables from .env file if it exists
-include .env

# Export variables for sub-make calls
export

# Configuration variables with defaults
SERVER_HOST ?= localhost
SERVER_PORT ?= 8080
SERVER_ARGS = pc gnik server $(SERVER_HOST) $(SERVER_PORT)

# Phony targets
.PHONY: help test compile clean install run-server install-run-server

# Default target
help:
	@echo "Available targets:"
	@echo "  help               - Show this help message"
	@echo "  test               - Run tests"
	@echo "  compile            - Compile the project"
	@echo "  clean              - Clean build artifacts"
	@echo "  install            - Clean and install the project"
	@echo "  run-server         - Run the server (default: $(SERVER_HOST):$(SERVER_PORT))"
	@echo "  install-run-server - Install and run the server"
	@echo ""
	@echo "Configuration:"
	@echo "  Override variables: make run-server SERVER_HOST=0.0.0.0 SERVER_PORT=9090"

test:
	mvn test

compile:
	mvn compile

clean:
	mvn clean

install:
	mvn clean install

run-server:
	mvn compile exec:java -Dexec.mainClass="com.noiprocs.App" -Dexec.args="$(SERVER_ARGS)"

install-run-server: install run-server
