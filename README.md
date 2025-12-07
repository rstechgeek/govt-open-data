# govt-data

This project is a government open data application consisting of a Spring Boot backend (`resource-service`) and a JavaFX frontend (`godfx`).

## Prerequisites

*   **Java 17+**: Required for `resource-service`.
*   **Java 21+**: Required for `godfx` (JavaFX).
*   **Maven**: For building both projects.
*   **Docker**: For running the PostgreSQL database.

## Quick Start

The easiest way to run the entire application is using the provided start script.

### 1. Run the Start Script
Open a terminal in the project root and run:
```bash
./start.sh
```

**What this script does:**
1.  Checks if Docker and Maven are installed.
2.  Starts a PostgreSQL container (`govt-postgres`) on port `5432`.
3.  Waits for the database to be ready.
4.  Starts `resource-service` in the background (logs to `resource-service.log`).
5.  Launches the `godfx` JavaFX application.

**Stopping:**
Closing the JavaFX application window will automatically stop the background `resource-service`. The Docker container remains running.

---

## IDE Import Instructions

### IntelliJ IDEA

1.  **Open Project**:
    *   Launch IntelliJ IDEA.
    *   Select **File > Open**.
    *   Navigate to the `govt-open-data` folder and click **Open**.
2.  **Import Maven Projects**:
    *   IntelliJ should detect the `pom.xml` files in `resource-service` and `godfx`.
    *   If not prompt appears, open the **Maven** tool window (usually on the right).
    *   Click the **+** (Add Maven Projects) icon.
    *   Select `resource-service/pom.xml` and `godfx/pom.xml`.
3.  **SDK Setup**:
    *   Go to **File > Project Structure > Project**.
    *   Ensure **SDK** is set to Java 17 or higher.
    *   Check **Modules** to ensure `resource-service` uses Java 17+ and `godfx` uses Java 21+.

### VS Code

1.  **Open Folder**:
    *   Launch VS Code.
    *   Select **File > Open Folder**.
    *   Select the `govt-open-data` folder.
2.  **Extensions**:
    *   Install the **Extension Pack for Java** (by Microsoft).
3.  **Import**:
    *   VS Code will detect the Maven projects and attempt to import them.
    *   Wait for the "Java Projects" view to populate in the Explorer.
4.  **Java Runtime**:
    *   Ensure you have configured proper Java runtimes in your settings (`java.configuration.runtimes` or just `JAVA_HOME`).
    *   `godfx` specifically requires Java 21+.

---

## Clean and Run Manually

### Resource Service (Backend)

1.  **Navigate**: `cd resource-service`
2.  **Clean & Build**: `mvn clean install`
3.  **Run**: `mvn spring-boot:run`
    *   **Note**: Ensure the database is running first (`docker start govt-postgres` or use `./start.sh`).

### GODFX (Frontend)

1.  **Navigate**: `cd godfx`
2.  **Clean**: `mvn clean`
3.  **Run**: `mvn javafx:run`

---

## Running Tests

To run unit and integration tests for `resource-service`:

```bash
cd resource-service
mvn test
```

This runs:
*   **Unit Tests**: Standard JUnit tests.
*   **Integration Tests**: Uses `Testcontainers` to spin up a temporary PostgreSQL instance for DB testing.
*   **External API Tests**: Uses `MockWebServer` to simulate external API responses.
