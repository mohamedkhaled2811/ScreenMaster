# ScreenMaster

ScreenMaster is a comprehensive theater management system built with Spring Boot. It provides a robust backend for managing theaters, screens, seats, and seat types, enabling efficient theater operations and ticket booking management.

## Features

- **Theater Management**: Create, retrieve, and delete theater locations
- **Screen Management**: Manage multiple screens within theaters with different configurations
- **Seat Management**: Configure seats with various types, accessibility options, and availability status
- **User Authentication**: Secure JWT-based authentication system
- **Role-Based Access Control**: Different permission levels for users and administrators

## Technologies Used

- **Java 21**: Modern Java with latest features
- **Spring Boot 3.5.4**: For rapid application development
- **Spring Data JPA**: For database interactions
- **Spring Security**: For authentication and authorization
- **PostgreSQL**: For data persistence
- **JWT (JSON Web Token)**: For secure authentication
- **Lombok**: To reduce boilerplate code
- **Maven**: For dependency management and build automation

## System Architecture

The application follows a layered architecture:

- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic
- **Repository Layer**: Manages data access operations
- **DTO Layer**: Data Transfer Objects for API request/response
- **Model Layer**: Entities that represent database tables

## Setup Instructions

### Prerequisites

- Java 21
- Maven
- PostgreSQL

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/ScreenMaster.git
   cd ScreenMaster
   ```

2. Configure the database connection in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/screenmaster
   spring.datasource.username=yourUsername
   spring.datasource.password=yourPassword
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build the application:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication

- `POST /auth/register` - Register a new user
- `POST /auth/login` - Authenticate a user

### Theater Management

- `GET /theater/get-theaters` - Get all theaters
- `POST /theater/add-theater` - Add a new theater
- `DELETE /theater/delete-theater/{theaterId}` - Delete a theater

### Screen Management

- `GET /theater/{theaterId}/screens` - Get all screens for a theater
- `POST /theater/add-screen` - Add a new screen
- `DELETE /theater/screen/{screenId}` - Delete a screen

### Seat Management

- `GET /theater/get-screen-seats/{screenId}` - Get all seats for a screen
- `POST /theater/add-screen-seat` - Add a new seat
- `DELETE /theater/seat/{seatId}` - Delete a seat

### Seat Type Management

- `GET /theater/get-seat-types` - Get all seat types
- `POST /theater/add-seat-type` - Add a new seat type
- `DELETE /theater/delete-seat-type/{seatTypeId}` - Delete a seat type

## DTO Structure

The application uses Data Transfer Objects (DTOs) to validate and transfer data between client and server:

- **TheaterRequestDto**: For creating/updating theaters
- **ScreenRequestDto**: For creating/updating screens
- **SeatRequestDto**: For creating/updating seats
- **SeatTypeRequestDto**: For creating/updating seat types

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributors

- GR74 Team

## Acknowledgements

- Spring Boot Team
- PostgreSQL Team
