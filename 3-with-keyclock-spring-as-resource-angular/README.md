# Full-Stack Example: Spring Boot + Angular + Keycloak

This folder contains a full-stack example with:
- A Spring Boot backend (see `spring` folder) acting as a resource server
- An Angular frontend (see `front` folder) for the user interface

Both parts are secured using Keycloak for authentication and authorization.

**How it works:**
- Users log in via Keycloak from the Angular app
- The backend validates access tokens from Keycloak
- The front applciation use angular-oauth2-oidc to implement the auth flow

Great for learning how to secure both frontend and backend in a modern web app.