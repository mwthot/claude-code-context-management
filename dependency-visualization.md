# E-Commerce Platform Dependency Visualization

## High-Level Architecture Overview

```mermaid
graph TB
    subgraph "External Layer"
        Client[Web/Mobile Clients]
        CDN[CloudFlare CDN]
    end
    
    subgraph "API Gateway Layer"
        Kong[Kong API Gateway<br/>:8000/:8001<br/>JWT Auth, Rate Limiting]
    end
    
    subgraph "Service Layer"
        US[User Service<br/>:8080<br/>30% Complete]
        OS[Order Service<br/>:8080<br/>75% Complete]
        PS[Product Service<br/>:8080<br/>Not Implemented]
        PAS[Payment Service<br/>:8080<br/>Not Implemented]
    end
    
    subgraph "Data Layer"
        PG[(PostgreSQL :5432<br/>3 Databases)]
        Redis[(Redis :6379<br/>Cache/Sessions)]
    end
    
    subgraph "Message Layer"
        RMQ[RabbitMQ :5672<br/>Event Bus]
    end
    
    Client --> CDN
    CDN --> Kong
    Kong --> US
    Kong --> OS
    Kong --> PS
    Kong --> PAS
    
    US --> PG
    US --> Redis
    US -.->|Events| RMQ
    
    OS --> PG
    OS --> Redis
    OS -->|REST: User Validation| US
    OS -.->|Future: Product Check| PS
    OS -.->|Future: Payment| PAS
    OS -.->|Events| RMQ
    
    PS -.-> PG
    PS -.-> Redis
    PS -.->|Events| RMQ
    
    PAS -.-> PG
    PAS -.->|Events| RMQ
    
    style US fill:#90EE90
    style OS fill:#FFD700
    style PS fill:#FFB6C1
    style PAS fill:#FFB6C1
    style Kong fill:#87CEEB
    style PG fill:#4682B4
    style Redis fill:#DC143C
    style RMQ fill:#FF8C00
```

## Service Communication Patterns

```mermaid
graph LR
    subgraph "Synchronous Communication (REST)"
        OS1[Order Service]
        US1[User Service]
        PS1[Product Service]
        PAS1[Payment Service]
        
        OS1 -->|GET /api/v1/users/{id}<br/>User Validation| US1
        OS1 -.->|GET /api/v1/products/{id}<br/>Product Details<br/>PLANNED| PS1
        OS1 -.->|POST /api/v1/payments<br/>Process Payment<br/>PLANNED| PAS1
        PAS1 -.->|GET /api/v1/users/{id}<br/>Account Verify<br/>PLANNED| US1
    end
    
    style OS1 fill:#FFD700
    style US1 fill:#90EE90
    style PS1 fill:#FFB6C1
    style PAS1 fill:#FFB6C1
```

```mermaid
graph LR
    subgraph "Asynchronous Communication (Events)"
        OS2[Order Service]
        US2[User Service]
        PS2[Product Service]
        PAS2[Payment Service]
        
        OS2 -.->|OrderCreated<br/>PLANNED| US2
        PAS2 -.->|PaymentConfirmed<br/>PLANNED| OS2
        US2 -.->|UserProfileUpdated<br/>PLANNED| PS2
        PS2 -.->|InventoryUpdated<br/>PLANNED| OS2
        US2 -.->|UserDeactivated<br/>PLANNED| OS2
        PAS2 -.->|PaymentFailed<br/>PLANNED| OS2
    end
    
    style OS2 fill:#FFD700
    style US2 fill:#90EE90
    style PS2 fill:#FFB6C1
    style PAS2 fill:#FFB6C1
```

## Database Dependencies

```mermaid
erDiagram
    USERS {
        UUID id PK
        VARCHAR email UK
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }
    
    USER_PROFILES {
        UUID user_id PK,FK
        VARCHAR first_name
        VARCHAR last_name
        VARCHAR phone
    }
    
    ORDERS {
        UUID id PK
        UUID user_id "Logical FK to USERS"
        VARCHAR status
        DECIMAL total_amount
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }
    
    ORDER_ITEMS {
        UUID id PK
        UUID order_id FK
        UUID product_id "Logical FK to PRODUCTS"
        INTEGER quantity
        DECIMAL unit_price
    }
    
    PRODUCTS {
        UUID id PK
        VARCHAR name
        DECIMAL price
        INTEGER inventory_count
    }
    
    PAYMENTS {
        UUID id PK
        UUID order_id "Logical FK to ORDERS"
        UUID user_id "Logical FK to USERS"
        DECIMAL amount
        VARCHAR status
    }
    
    USERS ||--o| USER_PROFILES : has
    ORDERS ||--o{ ORDER_ITEMS : contains
    ORDERS }o--|| USERS : "references (API validation)"
    ORDER_ITEMS }o--|| PRODUCTS : "references (future)"
    PAYMENTS }o--|| ORDERS : "processes"
    PAYMENTS }o--|| USERS : "charges"
```

## Deployment Dependencies

```mermaid
graph TB
    subgraph "Deployment Order"
        Step1[1. Infrastructure]
        Step2[2. User Service]
        Step3[3. Product Service]
        Step4[4. Payment Service]
        Step5[5. Order Service]
    end
    
    Step1 --> Step2
    Step2 --> Step3
    Step3 --> Step4
    Step4 --> Step5
    
    subgraph "Infrastructure Components"
        PG3[PostgreSQL<br/>Critical]
        R3[Redis<br/>High]
        RMQ3[RabbitMQ<br/>High]
        K3[Kong<br/>Critical]
    end
    
    Step1 -.-> PG3
    Step1 -.-> R3
    Step1 -.-> RMQ3
    Step1 -.-> K3
    
    style PG3 fill:#FF6B6B
    style K3 fill:#FF6B6B
    style R3 fill:#FFD93D
    style RMQ3 fill:#FFD93D
```

## Criticality Analysis

```mermaid
graph TD
    subgraph "Critical Components"
        C1[PostgreSQL<br/>System Failure]
        C2[Kong Gateway<br/>No External Access]
    end
    
    subgraph "High Priority"
        H1[User Service<br/>No Order Creation]
        H2[Redis Cache<br/>Performance Impact]
        H3[RabbitMQ<br/>No Async Processing]
    end
    
    subgraph "Medium Priority"
        M1[Product Service<br/>Limited Functionality]
        M2[Circuit Breakers<br/>No Resilience]
    end
    
    style C1 fill:#FF0000,color:#FFF
    style C2 fill:#FF0000,color:#FFF
    style H1 fill:#FFA500
    style H2 fill:#FFA500
    style H3 fill:#FFA500
    style M1 fill:#FFFF00
    style M2 fill:#FFFF00
```

## Missing Implementations

```mermaid
graph LR
    subgraph "Not Implemented"
        PS3[Product Service<br/>0% Complete]
        PAS3[Payment Service<br/>0% Complete]
        ES[Event System<br/>RabbitMQ Integration]
        CB[Circuit Breakers<br/>Resilience Patterns]
        USE[User Service<br/>API Endpoints]
    end
    
    subgraph "Impact"
        I1[No Product Validation]
        I2[No Payment Processing]
        I3[No Async Communication]
        I4[No Fault Tolerance]
        I5[No User Validation]
    end
    
    PS3 --> I1
    PAS3 --> I2
    ES --> I3
    CB --> I4
    USE --> I5
    
    style PS3 fill:#FF6B6B
    style PAS3 fill:#FF6B6B
    style ES fill:#FFD93D
    style CB fill:#6BCB77
    style USE fill:#FFD93D
```

## Kubernetes Deployment Architecture

```mermaid
graph TB
    subgraph "Kubernetes Namespace: ecommerce"
        subgraph "User Service Deployment"
            USP1[User Service Pod 1<br/>256Mi-512Mi RAM<br/>200m-500m CPU]
            USP2[User Service Pod 2<br/>256Mi-512Mi RAM<br/>200m-500m CPU]
            USP3[User Service Pod 3<br/>256Mi-512Mi RAM<br/>200m-500m CPU]
            USS[User Service<br/>ClusterIP:80]
        end
        
        subgraph "Order Service Deployment"
            OSP1[Order Service Pod 1]
            OSP2[Order Service Pod 2]
            OSP3[Order Service Pod 3]
            OSS[Order Service<br/>ClusterIP:80]
        end
        
        USS --> USP1
        USS --> USP2
        USS --> USP3
        
        OSS --> OSP1
        OSS --> OSP2
        OSS --> OSP3
    end
    
    subgraph "Health Checks"
        L[Liveness: /actuator/health<br/>Every 30s]
        R[Readiness: /actuator/health/readiness<br/>Every 10s]
    end
    
    USP1 -.-> L
    USP1 -.-> R
```

## Data Flow Diagram

```mermaid
sequenceDiagram
    participant Client
    participant Kong
    participant OrderService
    participant UserService
    participant ProductService
    participant PaymentService
    participant PostgreSQL
    participant RabbitMQ
    
    Client->>Kong: POST /api/v1/orders
    Kong->>OrderService: Forward Request (JWT Auth)
    
    OrderService->>UserService: GET /api/v1/users/{userId}
    UserService->>PostgreSQL: Query User
    PostgreSQL-->>UserService: User Data
    UserService-->>OrderService: User Validation Response
    
    Note over OrderService,ProductService: Future Implementation
    OrderService-->>ProductService: GET /api/v1/products/{productId}
    ProductService-->>OrderService: Product Details
    
    OrderService->>PostgreSQL: Save Order
    PostgreSQL-->>OrderService: Order Created
    
    Note over OrderService,PaymentService: Future Implementation
    OrderService-->>PaymentService: POST /api/v1/payments
    PaymentService-->>OrderService: Payment Confirmation
    
    OrderService->>RabbitMQ: Publish OrderCreated Event
    OrderService-->>Kong: Order Response
    Kong-->>Client: Order Created (201)
    
    Note over RabbitMQ,UserService: Async Processing
    RabbitMQ-->>UserService: OrderCreated Event
    UserService->>Client: Send Notification
```

## Legend

- 🟢 Green: Implemented and functional
- 🟡 Yellow: Partially implemented
- 🔴 Red: Not implemented / Critical component
- Solid Lines: Implemented dependencies
- Dotted Lines: Planned dependencies