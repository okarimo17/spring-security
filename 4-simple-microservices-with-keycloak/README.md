# Microservices Example with Keycloak

This folder contains a microservices architecture example, with multiple Spring Boot services and an Angular frontend, all secured by Keycloak.

**Components:**
- `admin-service`: Handles admin-specific APIs
- `user-service`: Handles user-specific APIs
- `api-gateway`: Routes requests to the correct service and handles authentication
- `angular-front`: Angular frontend for users
- `docker-compose.yaml`: Used to run all services and Keycloak together

**How it works:**
- All services are protected by Keycloak
- API Gateway validates tokens and routes requests
- Admin and user services provide role-based APIs

This is a practical example of securing microservices with a central identity provider.