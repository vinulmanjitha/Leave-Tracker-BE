

## ⚙️ **Backend — `README.md`**

```markdown
# ☕ Leave Management System - Backend

This is the **Spring Boot REST API** for the Leave Management System.  
It handles business logic, integrates with the Nager.Date public holiday API, and stores approved leaves in a Cloud database using Postgres.

---

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

## ⚙️ Environment Setup

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
