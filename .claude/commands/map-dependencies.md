---
allowed-tools: Bash(find:*), Bash(grep:*), Read, Glob, Write
description: Analyze and visualize service dependencies, database relationships, and cross-cutting concerns
---

Generate a comprehensive dependency map for this codebase including:

1. **Service Communication Analysis**: 
   - Parse @services/*/src for HTTP calls, event publishing/consuming
   - Identify synchronous vs asynchronous dependencies
   - Map API endpoints and their consumers

2. **Database Dependency Mapping**:
   - Analyze migration files for foreign key relationships
   - Identify shared data patterns across services
   - Document transaction boundaries

3. **Infrastructure Dependencies**:
   - Parse Docker Compose and Kubernetes configs
   - Map environment variable dependencies
   - Identify shared resources (Redis, RabbitMQ, etc.)

4. **Code-Level Dependencies**:
   - Analyze import statements across languages (Java, TypeScript, Python)
   - Map shared libraries and utilities
   - Identify circular dependencies

Create both a structured JSON output and a visual Mermaid diagram showing:
- Service interaction patterns
- Data flow directions
- Criticality levels (core vs optional dependencies)
- Deployment order requirements