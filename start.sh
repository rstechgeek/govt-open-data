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

# Kill likely stuck processes
echo "Cleaning up port 8091..."
lsof -ti:8091 | xargs kill -9 2>/dev/null || echo "No process found on port 8091."

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
if cd resource-service; then
    # Build resource-service
    echo "Building resource-service..."
    if ! mvn clean install -DskipTests; then
        echo "Error: resource-service build failed."
        exit 1
    fi

    # Run in background and save PID
    mvn spring-boot:run > ../resource-service.log 2>&1 &
    RESOURCE_PID=$!
    echo "resource-service starting with PID $RESOURCE_PID. Logs: resource-service.log"
    cd ..
else
    echo "Error: Directory resource-service not found."
    exit 1
fi

# Wait for resource-service to be ready
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
if cd godfx; then
    # Build godfx
    echo "Building godfx..."
    if ! mvn clean install -DskipTests; then
        echo "Error: godfx build failed."
        echo "Stopping resource-service..."
        kill $RESOURCE_PID
        exit 1
    fi

    echo "Running godfx..."
    mvn javafx:run | tee godfx.log
    
    # Cleanup on exit
    echo "godfx closed. Stopping resource-service..."
    kill $RESOURCE_PID
else
    echo "Error: Directory godfx not found."
    kill $RESOURCE_PID
    exit 1
fi
