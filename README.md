\# Product API



A secure RESTful Product Management API built with Java 17 and Spring Boot for the Zest India Backend Developer Hiring Assignment.



\## Tech Stack



\- Java 17

\- Spring Boot

\- Spring Data JPA / Hibernate

\- MySQL

\- Spring Security

\- JWT Authentication

\- Refresh Tokens

\- Jakarta Bean Validation

\- JUnit 5

\- Mockito

\- H2

\- Swagger / OpenAPI

\- Docker \& Docker Compose

\- Maven



\## Architecture



The application follows a layered architecture:



\- Controller — REST API endpoints

\- Service — Business logic

\- Repository — Database access using Spring Data JPA

\- Entity — JPA database models

\- DTO — Request and response objects

\- Security — JWT authentication and authorization

\- Exception — Global exception handling

\- Config — Security, CORS and OpenAPI configuration



\## API Endpoints



\### Authentication



| Method | Endpoint | Description |

|---|---|---|

| POST | `/api/v1/auth/login` | Login and receive access/refresh tokens |

| POST | `/api/v1/auth/refresh` | Refresh access token |



\### Products



| Method | Endpoint | Description |

|---|---|---|

| POST | `/api/v1/products` | Create product |

| GET | `/api/v1/products` | Get products with pagination |

| GET | `/api/v1/products/{id}` | Get product by ID |

| PUT | `/api/v1/products/{id}` | Update product |

| DELETE | `/api/v1/products/{id}` | Delete product |



\### Items



| Method | Endpoint | Description |

|---|---|---|

| GET | `/api/v1/products/{productId}/items` | Get items for a product |

| POST | `/api/v1/products/{productId}/items` | Create item |



\## Swagger



After starting the application, Swagger UI is available at:



`http://localhost:8080/swagger-ui.html`



\## Configuration



Create a `.env` file locally using `.env.example` as a reference.



Required environment variables:



\- `DB\_NAME`

\- `DB\_USERNAME`

\- `DB\_PASSWORD`

\- `JWT\_SECRET`

\- `JWT\_ACCESS\_EXPIRATION`

\- `JWT\_REFRESH\_EXPIRATION`



The `.env` file is intentionally excluded from Git.



\## Running Locally



Make sure MySQL is running and the required environment variables are configured.



Run:



```bash

./mvnw spring-boot:run

