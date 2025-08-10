# E-Commerce Platform - Project Context

## System Architecture Overview

The platform follows a microservices architecture with clear service boundaries:

Web UI (React/Next.js) → API Gateway (Kong) → Services
    ↓                           ↓                 ↓
CDN (CloudFlare)        Rate Limiting          User Service
SSL Termination         Authentication         Product Service
Static Assets           Load Balancing         Order Service
Payment Service

## Development Principles

- **Service Independence**: Each service owns its data and business logic
- **API-First Design**: All service communication through well-defined contracts
- **Event-Driven Architecture**: Async communication via message queues
- **Deployment Independence**: Services deploy independently via CI/CD

## Cross-Service Patterns

- **Authentication**: JWT tokens validated at API gateway, user context propagated
- **Data Consistency**: Event sourcing for order flows, eventual consistency across services
- **Error Handling**: Circuit breakers, retries with exponential backoff, graceful degradation
- **Monitoring**: Distributed tracing, service health checks, business metrics

## Key Documentation References
@docs/architecture-overview.md - Detailed system design decisions
@docs/service-dependencies.md - Service interaction patterns and contracts
@infrastructure/kubernetes/ - Production deployment and scaling configuration

## Dependency Context 
@.claude/dependency-map.json - Service relationships and integration patterns
@.claude/onboarding-paths.json - Role-based learning sequences

## Development Workflow
- Feature branches from `develop`, merge via pull request with required reviews
- Integration tests run in Docker containers via GitHub Actions
- Database migrations coordinated across services using schema versioning
- Blue-green deployments to Kubernetes clusters via GitOps (ArgoCD)