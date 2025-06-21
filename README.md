

```markdown
# Task Management API

A Spring Boot REST API for managing tasks and task lists. Built with Java 21, Spring MVC, and JPA for efficient task organization and tracking.

## 🚀 Features

- **Task Management**
  - Create, read tasks within task lists
  - Set priorities (HIGH, MEDIUM, LOW)
  - Track status (OPEN, CLOSED)
  - Set due dates
  - Task descriptions and titles

- **Task List Organization**
  - Create and manage multiple task lists
  - Track tasks within lists
  - Monitor progress and task counts
  - Organize tasks by lists

## 🛠️ Tech Stack

- **Backend**
  - Java 21
  - Spring Boot
  - Spring MVC
  - Spring Data JPA
  - Jakarta EE
  - Lombok

- **Data Validation**
  - Jakarta Validation
  - Custom validators

## 📁 Project Structure
```
plaintext
src/
├── main/
│   ├── java/com/LIVTech/tasks/
│   │   ├── controller/        # REST controllers
│   │   ├── domain/           
│   │   │   ├── dto/          # Data Transfer Objects
│   │   │   ├── entities/     # JPA Entities
│   │   │   └── mapper/       # Object Mappers
│   │   └── service/          # Business Logic
│   └── resources/            # Application properties
```
## 🔌 API Endpoints

### Task Lists
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/api/tasklists/list` | Get all task lists |
| POST   | `/api/tasklists/create` | Create task list |
| GET    | `/api/tasklists/{taskId}` | Get specific list |
| PUT    | `/api/tasklists/{taskId}/update` | Update list |
| DELETE | `/api/tasklists/{taskId}/delete` | Delete list |

### Tasks
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/task-list/{listId}/tasks` | Get tasks in list |
| POST   | `/task-list/{listId}/tasks` | Create new task |

## 📝 Request/Response Examples

### Create Task
```
http
POST /task-list/1/tasks
Content-Type: application/json

{
    "title": "Implement API",
    "description": "Create REST endpoints",
    "dueDate": "2025-07-01T10:00:00",
    "priority": "HIGH",
    "status": "OPEN"
}
```

```
http
HTTP/1.1 201 Created
Content-Type: application/json

{
    "data": {
        "id": 1,
        "title": "Implement API",
        "description": "Create REST endpoints",
        "dueDate": "2025-07-01T10:00:00",
        "priority": "HIGH",
        "status": "OPEN"
    },
    "message": "Task created successfully",
    "timestamp": "2025-06-21T14:35:00",
    "requestId": "550e8400-e29b-41d4-a716-446655440001"
}
```
## 🚦 Data Validation Rules

| Field | Rule |
|-------|------|
| Task Title | Minimum 2 characters |
| Task List Title | Minimum 2 characters |
| Priority | Must be: HIGH, MEDIUM, LOW |
| Status | Must be: OPEN, CLOSED |

## ⚙️ Setup Guide

1. **Prerequisites**
   ```bash
   java 21
   maven
   your preferred IDE
   ```

2. **Clone Repository**
   ```bash
   git clone <repository-url>
   cd tasks
   ```

3. **Build Project**
   ```bash
   mvn clean install
   ```

4. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

   Server starts at `http://localhost:8080`

## 🧪 Testing with Postman

1. **Get Tasks**
   - Method: GET
   - URL: `http://localhost:8080/task-list/1/tasks`
   - No body required

2. **Create Task**
   - Method: POST
   - URL: `http://localhost:8080/task-list/1/tasks`
   - Headers: 
     ```
     Content-Type: application/json
     ```
   - Body:
     ```json
     {
         "title": "New Task",
         "description": "Task Description",
         "dueDate": "2025-07-15T14:00:00",
         "priority": "HIGH",
         "status": "OPEN"
     }
     ```

## 🔍 Error Handling

| Status Code | Description |
|-------------|-------------|
| 200 | Success |
| 201 | Created |
| 400 | Bad Request |
| 404 | Not Found |
| 500 | Server Error |

## 📈 Future Enhancements

- [ ] Task completion tracking
- [ ] User authentication
- [ ] Task categories/tags
- [ ] Task search functionality
- [ ] Task comments/attachments

## 👥 Contributing

1. Fork repository
2. Create feature branch
   ```bash
   git checkout -b feature/YourFeature
   ```
3. Commit changes
   ```bash
   git commit -m 'Add YourFeature'
   ```
4. Push to branch
   ```bash
   git push origin feature/YourFeature
   ```
5. Open Pull Request

## 📄 License

[Add your license]

## 📞 Contact 

[Add your contact information]

---
Made with ❤️ by [Your Name]
```


