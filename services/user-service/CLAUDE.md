# User Service - Development Context

## Service Responsibilities
- User account lifecycle (registration, profile management, deactivation)
- Authentication and authorization (login, password reset, session management)
- User preferences and notification settings
- GDPR compliance (data export, deletion, consent management)

## Technology Stack
- **Framework**: Spring Boot 3.2 with Java 21
- **Database**: PostgreSQL 15 with Flyway migrations
- **Caching**: Redis for session storage and frequently accessed user data
- **Messaging**: RabbitMQ for user lifecycle events and notification triggers
- **Testing**: JUnit 5, Testcontainers for integration tests, Spring Cloud Contract for API contracts

## Domain Model and Patterns
- **Aggregate Root**: User entity with UserProfile and UserPreferences value objects
- **Repository Pattern**: Spring Data JPA with custom queries for complex operations
- **Domain Events**: Published via ApplicationEventPublisher for cross-service coordination
- **Command/Query Separation**: Separate handlers for write operations and read operations

## Integration Points
- **Publishes Events**: UserRegistered, UserProfileUpdated, UserDeactivated
- **Consumes Events**: OrderCompleted (for user activity tracking), PaymentFailed (for account status)
- **External APIs**: Email service for notifications, Identity verification service for KYC

## Key Implementation References
@src/main/java/domain/User.java - Core domain entity with business rules
@src/main/java/repository/UserRepository.java - Data access patterns and custom queries
@src/main/java/events/ - Domain event definitions and handlers
@src/test/java/integration/ - Integration test patterns and Testcontainer setup