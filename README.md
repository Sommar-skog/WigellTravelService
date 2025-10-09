# WigellTravelService

WigellTravelService is a **microservice** developed by **Amanda Aronsson** as part of the **Java Web Services group project**.  
It manages travel packages and bookings and integrates with the **WigellGateway**, which **Amanda Aronsson** also built.  
The service runs in a **Docker network** together with its own **MySQL container** and uses **Spring Boot with Basic Security**.

---

##  Overview

- **Language:** Java 21
- **Framework:** Spring Boot
- **Database:** MySQL (Docker container)
- **Security:** Spring Security (Basic Auth)
- **External API:** Currency Converter from [API Plugin](https://apiplugin.com/)

- **Ports:**
    - Travel Service → `8585`
    - Gateway → `4545`
    - MySQL → `3306`

---

##  Features

- CRUD operations for all entities except customers
- Travel and booking management
- Customer and admin endpoints
- Prices displayed in **SEK and EUR**
- Logging of all create, update and delete actions
- Fully containerized and networked with Gateway and MySQL
- Unit and integration tests with 100% coverage (service A, controller B and service B) (as per assignment)

---

##  Related Projects

| Project | Description |
|----------|-------------|
| [WigellGateway](https://github.com/Sommar-skog/WigellGateway) | API Gateway that routes requests to the microservices |
| MySQL Container | Database container connected in the same Docker network |

---

## External API

This project uses a public **Currency Converter API** provided by [API Plugin](https://apiplugin.com/)  
to calculate and display travel prices in both **SEK** and **EUR**.

---
## Example Endpoints

### Customer

- GET /api/wigelltravels/v1/travels – List available TravelPackages
- POST /api/wigelltravels/v1/booktrip – Book a trip
- PUT /api/wigelltravels/v1/canceltrip – Cancel a booking
- GET /api/wigelltravels/v1/mybookings – View active and past bookings

### Admin

- GET /api/wigelltravels/v1/travels – List available TravelPackages
- GET /api/wigelltravels/v1/listcanceled – List canceled bookings
- GET /api/wigelltravels/v1/listupcoming – List upcoming trips
- GET /api/wigelltravels/v1/listpast – List past trips
- POST /api/wigelltravels/v1/addtravel – Add new TravelPackage
- PUT /api/wigelltravels/v1/updatetravel – Update TravelPackage
- PUT /api/wigelltravels/v1/removetravel/{id} – Set TravelPackage to inactive
