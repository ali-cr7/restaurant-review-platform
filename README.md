# Restaurant Review Platform

A robust Spring Boot application for managing restaurant data, storing and retrieving photos, and supporting secure authentication and integration with Elasticsearch and Keycloak. Designed for scalable restaurant review and management.

---

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot 3**
- **Spring Security** (with JWT, integrates with Keycloak)
- **Spring Data Elasticsearch**
- **Spring Web**
- **Maven**
- **Lombok**
- **MapStruct** (for DTO mapping)
- **Docker Compose** (for running Elasticsearch, Kibana, and Keycloak in development)

---

## ✨ Features

- JWT-secured REST API endpoints
- Photo upload, storage, and metadata retrieval
- Restaurant, Review, Photo, Address, and User domain modeling
- Elasticsearch-powered search and indexing for restaurants
- Role-based access control via Keycloak
- Integration-ready and easy to extend

---

## ⚙️ Prerequisites

- Java 21+
- Maven
- Docker & Docker Compose
- (Optional for auth) A browser for accessing the Keycloak admin panel

---

## 🏁 Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/<your-username>/restaurant-review-platform.git
   cd restaurant-review-platform
   ```

2. **Start development services** (Elasticsearch, Kibana, Keycloak):
   ```bash
   docker-compose up -d
   ```

3. **Build and run the application:**
   ```bash
   ./mvnw spring-boot:run
   # or (if using Windows)
   mvnw.cmd spring-boot:run
   ```
   > Or import as a Maven project into your favorite IDE and run `RestaurantApplication.java`.

4. **Access Keycloak Admin:** [http://localhost:9090](http://localhost:9090)
   - Username: `admin`
   - Password: `admin`

---

## 📚 API Endpoints (Main)

| Method | Endpoint                                              | Description                                       |
|--------|------------------------------------------------------|---------------------------------------------------|
| POST   | `/api/photos`                                        | Upload a photo                                    |
| GET    | `/api/photos/{id}`                                   | Get/download a photo by ID                        |
| POST   | `/api/restaurants`                                   | Create a new restaurant                           |
| GET    | `/api/restaurants`                                   | Search/List restaurants (with filtering params)    |
| GET    | `/api/restaurants/{restaurantId}`                    | Get details for a specific restaurant             |
| PUT    | `/api/restaurants/{restaurantId}`                    | Update a restaurant                               |
| DELETE | `/api/restaurants/{restaurantId}`                    | Delete a restaurant                               |
| POST   | `/api/restaurants/{restaurantId}/reviews`            | Create a review on a restaurant                   |
| GET    | `/api/restaurants/{restaurantId}/reviews`            | List reviews for a restaurant                     |
| GET    | `/api/restaurants/{restaurantId}/reviews/{reviewId}` | Get a specific review by ID                       |
| PUT    | `/api/restaurants/{restaurantId}/reviews/{reviewId}` | Update a restaurant review                        |
| DELETE | `/api/restaurants/{restaurantId}/reviews/{reviewId}` | Delete a restaurant review                        |

> **Note:**
> Most endpoints are secured via JWT. You must provide a valid Bearer token, usually obtained from Keycloak login.

---

## 🔮 Future Enhancements

- User registration and management via Keycloak
- Admin dashboards and moderation tools
- Rating aggregation and analytics
- Restaurant geolocation search
- Advanced filtering and sorting
- Deployment scripts for cloud providers

---

## 📝 License
See [LICENSE](LICENSE) file for details.
