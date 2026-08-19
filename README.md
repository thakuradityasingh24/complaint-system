# Complaint System

A web-based Complaint Management System built using Java and Spring Boot. The application provides a backend for user authentication, complaint submission and management, and secure access using Spring Security and JWT.

## 🎯 Project Overview

The Complaint System is designed to provide a structured platform for managing user complaints.

Users can authenticate themselves and interact with the complaint management system through REST APIs. The backend follows a layered architecture with separate components for repositories, services, security, and authentication.

## 🎯 Objectives

* Provide secure user authentication and authorization.
* Allow users to submit and manage complaints.
* Provide REST APIs for complaint and user operations.
* Implement JWT-based authentication.
* Secure APIs using Spring Security.
* Maintain a structured and maintainable backend architecture.
* Provide automated testing for application functionality.

## ✨ Key Features

* User registration and login
* User authentication and authorization
* JWT-based authentication
* Spring Security integration
* Complaint submission
* Complaint management
* User management
* Repository-based database interaction
* REST API based backend
* Service-layer architecture
* Automated application testing

## 🏗️ Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/cspdcl/complaint_system/
│   │       ├── repository/
│   │       │   ├── ComplaintRepository.java
│   │       │   └── UserRepository.java
│   │       │
│   │       ├── security/
│   │       │   ├── SecurityConfig.java
│   │       │   └── UserDetailsServiceImpl.java
│   │       │
│   │       └── service/
│   │           ├── AuthService.java
│   │           ├── ComplaintService.java
│   │           └── JwtService.java
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/
        └── com/cspdcl/complaint_system/
            └── ComplaintSystemApplicationTests.java
```

## 🔐 Authentication & Security

The application uses Spring Security and JWT to secure user authentication and protected API resources.

The authentication flow includes:

1. User registration/login
2. Authentication verification
3. JWT generation
4. JWT-based request authentication
5. Authorization of protected resources

## 🛠️ Technologies Used

* Java
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* Maven
* REST APIs
* Git
* GitHub

## 🚀 Getting Started

### Prerequisites

Make sure the following are installed:

* Java JDK
* Git
* Maven (optional, since Maven Wrapper is included)

### Clone the Repository

```bash
git clone https://github.com/thakuradityasingh24/complaint-system.git
```

### Navigate to the Project

```bash
cd complaint-system
```

### Run the Application

On Windows:

```bash
mvnw.cmd spring-boot:run
```

### Run Tests

```bash
mvnw.cmd test
```

## 🧪 Testing

The project includes test classes under:

```text
src/test/
```

Tests can be executed using the Maven Wrapper.

## 🔮 Future Scope

Possible future improvements include:

* Admin dashboard
* Complaint status tracking
* Complaint priority management
* Email/SMS notifications
* Role-based dashboards
* Advanced complaint search and filtering
* Complaint history and analytics
* File/image attachment support
* Deployment using cloud services

## 📌 Project Highlights

* Layered Spring Boot architecture
* Secure authentication using JWT
* Spring Security implementation
* RESTful backend APIs
* Database interaction using Spring Data JPA
* Maven-based project management
* Automated testing
* Version controlled using Git and GitHub

## 👨‍💻 Author

**Aditya Singh Thakur**

B.Tech Computer Science Engineering

GitHub: https://github.com/thakuradityasingh24
