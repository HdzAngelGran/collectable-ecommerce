# Collectable E-commerce API

This project is a simple REST API for a collectable e-commerce platform. It allows managing users and items through a set of defined endpoints.

## Project Purpose

The main purpose of this project is to provide a backend service for a collectable e-commerce application. It handles the creation, retrieval, update, and deletion of users and items.

## Project Structure

The project follows a standard Maven project structure:

```
collectable-ecommerce/
├── .gitignore
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── arkn37/
│   │   │           ├── Main.java
│   │   │           ├── controller/
│   │   │           ├── dto/
│   │   │           ├── exception/
│   │   │           ├── model/
│   │   │           ├── repository/
│   │   │           ├── service/
│   │   │           └── utils/
│   │   └── resources/
│   └── test/
└── target/
```

- **`src/main/java`**: Contains the main source code of the application.
- **`controller`**: Contains the API controllers that handle HTTP requests.
- **`dto`**: Contains Data Transfer Objects used for API requests and responses.
- **`exception`**: Contains custom exception classes.
- **`model`**: Contains the data models (entities).
- **`repository`**: Contains the data access layer (repositories).
- **`service`**: Contains the business logic of the application.
- **`utils`**: Contains utility classes.
- **`pom.xml`**: The Project Object Model file for Maven, which defines project dependencies and build settings.

## How to Run the Project

1. **Prerequisites:**

   - Java 17 or higher
   - Maven 3.6.0 or higher

2. **Build the project:**
   Open a terminal in the project's root directory and run the following command to build the project and create a JAR file:

   ```bash
   mvn clean install
   ```

3. **Run the application:**
   After a successful build, you can run the application using the following command:
   ```bash
   java -jar target/collectable-ecommers-1.0-SNAPSHOT.jar
   ```
   The application will start on port `8080`.

## API Endpoints

The API is versioned and all endpoints are available under the `/api/v1` path.

### User Endpoints

- `GET /api/v1/users`: Get a list of users. Supports filtering.
- `GET /api/v1/users/{uuid}`: Get a user by their UUID.
- `POST /api/v1/users`: Create a new user.
- `PUT /api/v1/users/{uuid}`: Update an existing user.
- `DELETE /api/v1/users/{uuid}`: Delete a user.
- `OPTIONS /api/v1/users/{uuid}`: Check if a user exists.

### Item Endpoints

- `GET /api/v1/items`: Get a list of items. Supports filtering.
- `GET /api/v1/items/{uuid}`: Get an item by its UUID.
- `POST /api/v1/items`: Create a new item.
