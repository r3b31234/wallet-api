# wallet-api
Wallet API - Spring Boot

RESTful API desarrollada con Spring Boot 3 y Java 17, enfocada en arquitectura limpia, buenas prácticas backend y optimización de performance.

Features

CRUD de Customers
Gestión de Orders con relaciones (OneToMany)
Paginación y filtrado (Pageable)
Validación de datos (Jakarta Validation)
Manejo global de excepciones
Autenticación con JWT
Base de datos en memoria (H2)
Testing con Mockito y JUnit
Optimización de queries (N+1 problem)


Stack Tecnológico

Java 17
Spring Boot 3
Spring Data JPA
Spring Security
H2 Database
Maven
Mockito / JUnit


Arquitectura

Controller -> Service -> Repository -> Database
Separación de responsabilidades
Uso de DTOs para exposición de datos
Mappers para transformación de entidades


Seguridad

Generación de token JWT
Endpoints protegidos
Configuración personalizada de Spring Security


Conceptos implementados

Principios SOLID
Dependency Injection (DIP)
Transacciones (@Transactional)
Paginación eficiente
Optimización con JOIN FETCH
Prevención del N+1 problem

Cómo ejecutar
mvn clean install
mvn spring-boot:run

Endpoints principales
POST   /customers
GET    /customers?page=0&size=5

POST   /orders
GET    /orders/{id}
GET    /orders?page=0&size=5&status=CREATED

POST   /auth/login


H2 Console
http://localhost:8080/h2-console

Credenciales por defecto:

JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (vacío)

Ejemplos de request 
{ "customerId": 1, "items": [ { "product": "Laptop", "price": 1200, "quantity": 1 } ] }
{"username": "luis"}

Estado del proyecto
Proyecto funcional con arquitectura lista para escalar y extender a entornos productivos.

Autor
Luis Angel Espina Herrnandez
