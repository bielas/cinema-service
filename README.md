# Cinema Service Application

## Overview

The **Cinema Service Application** is designed to manage movie schedules, pricing, and ratings for a cinema specializing in the *Fast & Furious* franchise. It integrates with the IMDb API to fetch movie details and provides a RESTful interface for managing and retrieving cinema data.

## Running the Application

### Prerequisites
* Java 17+
* Gradle

### Run the Application
```bash
./gradlew bootRun --args='--imdb.api.apiKey=IMDB_API_KEY'
```

## Testing

### Running Unit, Integration and E2E Tests
```bash
./gradlew test
```

## Access the API

### API Endpoints
* **H2 Console**: http://localhost:8080/h2-console
* **Swagger**: http://localhost:8080/swagger-ui/index.html

### H2 Database Configuration
* **JDBC URL**: `jdbc:h2:mem:cinema-db`
* **Username**: `sa`
* **Password**: (leave blank)

### Flyway Migrations
* Flyway ensures the database schema is versioned and consistent. Migration scripts are located in:

### Continuous Integration (CI)
* Runs every commit pushed to main branch and pull requests to main branch

## Features

* **Movie Catalog Management**
  * Internal endpoints for updating showtimes and prices.
  * Support for assigning different prices to the same movie based on showtimes.
* **Movie Ratings**
  * Customers can submit ratings (1-5 stars) for movies they have watched.
* **IMDb API Integration**
  * Fetches movie details:
    * Name
    * Description
    * Release date
    * IMDb rating
    * Runtime
* **Placeholder Authentication**
  * A dummy authentication interface is implemented for testing purposes.
* **H2 Database**
  * An in-memory database is used for local development and testing.
* **Flyway Integration**
  * Database schema management is handled using Flyway for consistent migrations across environments.

## Implementation Notes

### Authentication
* A placeholder for authentication is provided, returning dummy user data.
* Future implementations should adhere to specific authentication and authorization requirements.

### Endpoint Security
* **`/self/movies`**: This endpoint should be secured with roles or permissions to restrict access.
* **`/movies/{movieId}/addMovieRating`**: This endpoint should ensure that only authenticated users with valid accounts can add ratings.

### IMDb API Integration
* Results from the IMDb API are not cached in this implementation.
* Add caching to improve performance and reduce external API calls, as movie details are unlikely to change frequently.

### Showtimes and Pricing
* The design assumes that each showtime can have a different ticket price.
  * This allows cinemas to set higher prices for peak hours while offering discounts during off-peak times.

### Error Handling
* Exception handling is minimal in this version.
* Implement proper exception handling to ensure consistent, meaningful error responses.

### H2 Database
* The application uses an in-memory H2 database for local testing.
* Flyway is used to manage schema versions and initial data setup.

### Mapping
* No automatic mapping libraries (e.g., MapStruct) are used.
* Manual mapping is implemented for greater control over the transformation process.

## Future Enhancements (TODOs)
1. Implement proper authentication and authorization mechanisms.
2. Secure endpoints with role-based access control.
3. Improve caching for IMDb API responses in ImdbMovieRepository.
4. Improve error handling with a consistent exception response mechanism.
6. Explore the use of mapping libraries for reducing boilerplate while maintaining control.
7. Add environment profiles for better managing env variables
8. Add TestContainers in tests
9. Improve CI/CD pipeline
10. Better env variables management (eg. via aws secret manager)

## Decision to Use suspend Functions and Coroutines

Although the current database relies on blocking queries, I chose to use suspend functions and coroutines to future-proof the system:

* If we switch to a non-blocking database (e.g., R2DBC) in the future, the architecture will already be compatible. This minimizes the effort required to implement such changes.
* Coroutines allow asynchronous operations to be written in a style that looks like synchronous code, making it easier to read, understand, and maintain.
* Other parts of the system, like external API integrations (e.g., using WebClient), already use non-blocking approaches. Coroutines ensure a consistent architectural style across the application.

This approach ensures flexibility, scalability, and prepares the system for modern, non-blocking technologies without compromising the current implementation.
