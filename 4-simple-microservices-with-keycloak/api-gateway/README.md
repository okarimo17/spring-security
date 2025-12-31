# API Gateway

This is the API Gateway for the microservices example. It routes requests to the correct service and handles authentication using Keycloak.

**Key features:**
- Routes requests to admin and user services
- Validates JWT tokens from Keycloak
- Central entry point for all APIs

Use this as a pattern for securing and routing in microservices architectures.