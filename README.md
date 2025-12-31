# Spring Security Projects Overview

This repository contains several example projects demonstrating authentication and authorization using Spring Security and Keycloak, as well as microservices architecture. Each folder is a self-contained project or module, described below in simple terms.

## Main Folders

- **0-simple jwt-based auth**: Basic Spring Boot project showing how to implement JWT-based authentication.
- **1-with-keyclock-spring-as-client**: Example of a Spring Boot app acting as a client to Keycloak for authentication.
- **2-with-keyclock-spring-as-resource**: Spring Boot project as a resource server, protected by Keycloak.
- **3-with-keyclock-spring-as-resource-angular**: Full-stack example with a Spring Boot backend (resource server) and Angular frontend, both secured with Keycloak.
- **4-simple-microservices-with-keycloak**: Simple microservice application with multiple Spring Boot services (admin, user, API gateway) and an Angular frontend, all secured with Keycloak.
- **docker-compose.yml**: Used to run Keycloak and other services locally for testing.

See each folder for more details.