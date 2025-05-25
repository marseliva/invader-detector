# Invader detector

A Java Spring Boot application for detecting invaders in radar data.  
Provides CRUD operations for Invader and detecting invaders in radar data operation.
In the task description, “radar images” were shown as examples, but since the provided inputs are text-based, the application currently parses and analyzes either a TXT file or a JSON object. If future requirements call for actual image input, image‐to‐text parsing (for example, using Tesseract OCR) can be integrated into the existing project structure with minimal changes.

---

## Project Structure

```plaintext
invaders-detector/
├── src/
    ├── main/
        ├── java/      # Java source code
        └── resources/ # Resource files
    └── test/
        ├── java/      # Test source code
        └── resources/
├── build.gradle.kts      # Gradle build configuration
├── Dockerfile        # Docker build file
├── docker-compose.yml # Docker Compose file
├── README.md         # Project overview

```

## Installation & Setup

### Using Gradle

```bash
git clone https://github.com/marseliva/invader-detector

cd invader-detector

./gradlew clean build

docker-compose up -d

docker-compose down
```

## Components Used

- **Java 21**
- **PostgreSQL 15**
- **Spring Boot**

## Core Features

- **CRUD operations on Invader**: Full Create, Read, Update, Delete operations on a single entity `Invader`.
- **Swagger Documentation**: Explore API via [Swagger UI](http://localhost:8080/swagger-ui/index.html).
- **Detecting invaders in radar data**: Import radar data by uploading a txt file via `/api/detect`.

## API Endpoints

- `POST /invader` — Create Invader through JSON or txt file
- `GET /invader` — List all Invaders.
- `GET /invader/{id}` — Get a specific Invader by ID.
- `DELETE /invader/{id}` — Delete a Invader.
- `POST /detect` — Detect invaders in JSON or TXT radar data.

## Future Improvements

- [ ] Add more unit and integration tests to improve code quality and stability.
- [ ] Improve API interface (remove path variables from POST requests)
- [ ] Improve exception handling
