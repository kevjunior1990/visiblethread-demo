# VisibleThreadDemo Application

## 📌 Overview

This project is a **Spring Boot** application that provides APIs for managing users, teams, and documents. It integrates with **PostgreSQL** for data storage and includes an external **Gemini AI** model service.

## 🏗️ Technology Stack

- **Java 17**
- **Spring Boot** (Spring MVC, JPA)
- **PostgreSQL** (Relational Database)
- **JUnit** (Testing Framework)
- **Docker** (Containerization)
- **Docker Compose** (Multi-container deployment)

---

## 🚀 Build and Deploy Instructions

### **🔹 Step 1: Build the Application**

To build the application, run the following command from the project root:

```sh
./mvnw clean package -Ddocker-image.tag=latest
```

This command:

- Cleans the project
- Packages it into a JAR file
- Builds a Docker image with the **latest** tag

### **🔹 Step 2: Deploy with Docker Compose**

Once the build is complete, deploy the application using:

```sh
docker-compose up -d
```

This will:

- Start **PostgreSQL** database
- Start the **Spring Boot application** in a Docker container

### **🔹 Step 3: Stop and Remove Containers**

To bring down the application, run:

```sh
docker-compose down
```

This stops and removes all running containers.

---

## 📂 Database Schema

The PostgreSQL database consists of the following tables:

### **vt\_team** (Teams Table)

| Column | Type         | Constraints                 |
| ------ | ------------ | --------------------------- |
| id     | BIGINT       | PRIMARY KEY, AUTO-INCREMENT |
| name   | VARCHAR(255) | UNIQUE, NOT NULL            |

### **vt\_user** (Users Table)

| Column             | Type         | Constraints                 |
| ------------------ | ------------ | --------------------------- |
| id                 | BIGINT       | PRIMARY KEY, AUTO-INCREMENT |
| email              | VARCHAR(255) | UNIQUE, NOT NULL            |
| creation\_date     | TIMESTAMP    | DEFAULT CURRENT\_TIMESTAMP  |
| modification\_date | TIMESTAMP    | DEFAULT CURRENT\_TIMESTAMP  |

### **vt\_user\_team** (User-Team Mapping Table)

| Column   | Type   | Constraints                                    |
| -------- | ------ | ---------------------------------------------- |
| user\_id | BIGINT | FOREIGN KEY -> vt\_user(id), ON DELETE CASCADE |
| team\_id | BIGINT | FOREIGN KEY -> vt\_team(id), ON DELETE CASCADE |

### **vt\_document** (Documents Table)

| Column             | Type         | Constraints                           |
| ------------------ | ------------ | ------------------------------------- |
| id                 | BIGINT       | PRIMARY KEY, AUTO-INCREMENT           |
| name               | VARCHAR(255) | UNIQUE, NOT NULL, must contain `.txt` |
| content            | TEXT         |                                       |
| word\_count        | BIGINT       | DEFAULT 0                             |
| user\_id           | BIGINT       | FOREIGN KEY -> vt\_user(id), NOT NULL |
| creation\_date     | TIMESTAMP    | DEFAULT CURRENT\_TIMESTAMP            |
| modification\_date | TIMESTAMP    | DEFAULT CURRENT\_TIMESTAMP            |

---

## 📊 Sample Data Inserted

| Team Name   |
| ----------- |
| Engineering |
| Marketing   |

| User Email                                     | Associated Teams       |
| ---------------------------------------------- | ---------------------- |
| [user1@example.com](mailto\:user1@example.com) | Engineering            |
| [user2@example.com](mailto\:user2@example.com) | Marketing              |
| [user3@example.com](mailto\:user3@example.com) | Engineering, Marketing |
| [user4@example.com](mailto\:user4@example.com) | None                   |
| [user5@example.com](mailto\:user5@example.com) | None                   |

| Document Name           | Associated User                                |
| ----------------------- | ---------------------------------------------- |
| project\_plan.txt       | [user1@example.com](mailto\:user1@example.com) |
| marketing\_strategy.txt | [user2@example.com](mailto\:user2@example.com) |
| research\_notes.txt     | [user3@example.com](mailto\:user3@example.com) |

---

## 📡 API Endpoints

### **DocumentController**

1. **Get all documents**
   ```http
   GET /api/v1/document/
   ```
2. **Upload a new document**
   ```http
   POST /api/v1/document/
   ```
   **Params:** file, email
3. **Get word count of a document**
   ```http
   GET /api/v1/document/word/count?fileName=project_plan.txt
   ```

### **GeminiController**

4. **Query Gemini AI with a file**
   ```http
   GET /api/v1/gemini/
   ```
   **Params:** file (multipart)

### **TeamController**

5. **Get all teams**
   ```http
   GET /api/v1/team/
   ```
6. **Get a specific team by name**
   ```http
   GET /api/v1/team/{name}
   ```
7. **Add a new team**
   ```http
   POST /api/v1/team/
   ```
   **Body:** `{ "name": "Product" }`

### **UserController**

8. **Get users without documents within a date range**
   ```http
   GET /api/v1/user/?startDate=1700000000000&endDate=1705000000000
   ```
9. **Add a new user**
   ```http
   POST /api/v1/user/
   ```
   **Body:** `{ "email": "new.user@example.com", "teamNames": ["Engineering"] }`
10. **Update an existing user**
    ```http
    PUT /api/v1/user/
    ```
    **Body:** `{ "email": "john.doe@example.com", "teamNames": ["Marketing"] }`
11. **Delete a user**
    ```http
    DELETE /api/v1/user/?email=jane.smith@example.com
    ```

---

## 🔍 Testing

Run tests using:

```sh
./mvnw test
```

This executes unit tests (JUnit) to verify API behavior.


