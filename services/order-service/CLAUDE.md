# Order Service - Development Context

## Service Responsibilities
- Order lifecycle management (creation, status tracking, fulfillment coordination)
- Shopping cart persistence and checkout flow
- Order item aggregation with pricing calculations
- Cross-service validation (user verification, product availability)
- Order history and query capabilities

## Technology Stack
- **Framework**: Spring Boot 3.2 with Java 21
- **Database**: PostgreSQL 15 with Flyway migrations
- **HTTP Client**: RestTemplate for synchronous service calls
- **Caching**: Redis for cart session storage (planned)
- **Messaging**: RabbitMQ for order events (OrderCreated, OrderShipped, OrderCompleted)
- **Testing**: JUnit 5, Testcontainers for integration tests, RestAssured for API tests

## Domain Model and Patterns
- **Aggregate Root**: Order entity with OrderItem collection
- **Value Objects**: OrderItem with quantity, unit price, and subtotal calculation
- **Repository Pattern**: Spring Data JPA with custom queries for order retrieval
- **Service Layer**: OrderService handles business logic and cross-service coordination
- **Domain Events**: OrderCreated, OrderUpdated, OrderCancelled (to be implemented)

## Integration Points
- **Publishes Events**: 
  - OrderCreated (includes userId, orderId, items, totalAmount)
  - OrderStatusChanged (includes orderId, oldStatus, newStatus)
  - OrderCancelled (includes orderId, reason)
- **Consumes Events**: 
  - PaymentConfirmed (to update order status to PAID)
  - ShipmentDispatched (to update order status to SHIPPED)
  - ProductPriceChanged (for active cart recalculation - planned)
- **External APIs**: 
  - User Service for validation
  - Payment Service for processing (planned)
  - Notification Service for order confirmations (planned)

## Key Implementation References
@src/main/java/domain/Order.java - Order aggregate root with business logic
@src/main/java/domain/OrderItem.java - Order line item value object
@src/main/java/repository/OrderRepository.java - Data access patterns
@src/main/java/service/OrderService.java - Business logic and orchestration
@src/main/java/service/UserValidationService.java - Cross-service communication
@src/main/java/controller/OrderController.java - REST API endpoints
@src/main/resources/db/migration/ - Database migration scripts
@src/main/resources/application.yml - Service configuration