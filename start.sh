#!/bin/bash

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check for Docker
if ! command_exists docker; then
    echo "Error: Docker is not installed or not in PATH."
    exit 1
fi

# Check for Maven
if ! command_exists mvn; then
    echo "Error: Maven is not installed or not in PATH."
    exit 1
fi

# Start PostgreSQL container
echo "Starting PostgreSQL container..."
if [ "$(docker ps -q -f name=govt-postgres)" ]; then
    echo "Container govt-postgres is already running."
else
    if [ "$(docker ps -aq -f name=govt-postgres)" ]; then
        echo "Starting existing govt-postgres container..."
        docker start govt-postgres
    else
        echo "Creating and starting new govt-postgres container..."
        docker run -d \
            --name govt-postgres \
            -p 5432:5432 \
            -e POSTGRES_USER=ravi \
            -e POSTGRES_PASSWORD=ravi \
            -e POSTGRES_DB=govtdb \
            -v "$(pwd)/postgres.sql:/docker-entrypoint-initdb.d/postgres.sql" \
            postgres:15
    fi
fi

# Wait for PostgreSQL to be ready
echo "Waiting for PostgreSQL to be ready..."
until docker exec govt-postgres pg_isready -U ravi; do
    echo "Waiting for database..."
    sleep 2
done
echo "PostgreSQL is ready."

# Start resource-service
echo "Starting resource-service..."
cd resource-service
# Run in background and save PID
mvn spring-boot:run > ../resource-service.log 2>&1 &
RESOURCE_PID=$!
echo "resource-service starting with PID $RESOURCE_PID. Logs: resource-service.log"

# Wait for resource-service to be ready (naive check: wait for port 8091)
echo "Waiting for resource-service to start on port 8091..."
TIMEOUT=60
COUNTER=0
while ! nc -z localhost 8091; do
    sleep 1
    COUNTER=$((COUNTER + 1))
    if [ $COUNTER -ge $TIMEOUT ]; then
        echo "Error: resource-service failed to start within $TIMEOUT seconds."
        kill $RESOURCE_PID
        exit 1
    fi
done
echo "resource-service is running."

# Start godfx
echo "Starting godfx..."
cd ../godfx
mvn javafx:run

# Cleanup on exit (optional, depending on preference. Usually we might want to keep services running)
# For this script, since godfx is the UI, maybe we want to kill resource-service when godfx closes?
echo "godfx closed. Stopping resource-service..."
kill $RESOURCE_PID
