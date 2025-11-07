

##  **Backend — `README.md`**

```markdown
# ☕ Leave Management System - Backend

This is the **Spring Boot REST API** for the Leave Management System.  
It handles business logic, integrates with the Nager.Date public holiday API, and stores approved leaves in a Cloud database using Postgres.

---

# ☕ Leave Management System - Backend

This repository contains the **Spring Boot REST API** for the **Leave Management System**.  
It handles leave request submissions, validation, approval workflows, integrates with the **Nager.Date public holiday API**, sends email notifications, and stores all data securely in a **PostgreSQL Cloud Database (AWS RDS)**.

---

##  Architecture Overview
![Architecture Diagram](https://github.com/vinulmanjitha/Leave-Tracker-BE/blob/0d0f0a2c6f0a6a79009b90dc0e29ee3fda6f290c/Blank%20diagram.png?raw=true)


## 🧩 Responsibilities

- Validate leave requests  
- Call public holiday API to avoid overlaps  
- Save approved leaves  
- Send notification  

---

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Nager.Date API** (for public holidays)

---

##  Environment Setup

### Prerequisites
- Java 17+
- Maven 3.9+
- PostgreSQL 14+
- IDE (IntelliJ / VS Code / Eclipse)

---

### Setup Instructions

1. Clone the backend repository:
   ```bash
   git clone https://github.com/your-username/leave-management-backend.git
   cd leave-management-backend

### Local Deployment

Install PostgreSQL and create a database leavetracker.
Configure credentials in application.properties.
Run the app:
mvn spring-boot:run
Access API at http://localhost:8080
.

###Known Limitations

Authentication and role-based authorization not yet implemented.
Email service runs synchronously (may delay responses).
Single-region deployment.

###Future Developments

| Area             | Improvement Idea                                                    |
| ---------------- | ------------------------------------------------------------------- |
| **Security**     | Add JWT-based authentication, HTTPS, role-based access (Admin/User) |
| **Scalability**  | Deploy with Docker & Kubernetes for auto-scaling                    |
| **Monitoring**   | Use Spring Boot Actuators       |
| **Logging**      | Add centralized logging (AWS CloudWatch)               |
| **Performance**  | Enable async email service and caching for holiday data             |
| **Multi-Region** | Deploy across multiple AWS regions with RDS read replicas           |
| **CI/CD**        | Automate deployment with GitHub Actions or AWS CodePipeline         |

### Application-Level Scalability
Cache holiday data in-memory (Redis or Spring Cache) for a flexible duration
Asynchronous Processing (Emails)
Database Optimization
Multi-Region Deployment in AWS
