🎬 Movie Ticket Management System

A full-stack Movie Ticket Management System built with Spring Boot (Backend) and Angular (Frontend).
This project allows users to book, view, update, and cancel movie tickets with authentication and role-based access.

🚀 Tech Stack
🔹 Backend (Spring Boot)

Java 17+

Spring Boot

Spring Security (Authentication & Authorization)

Spring Data JPA / Hibernate

MySQL (or any relational DB)

Maven

🔹 Frontend (Angular)

Angular

TypeScript

HTML / CSS

Bootstrap

✨ Features

✔️ User Registration & Login (Authentication)
✔️ Book Movie Tickets with Time Slots
✔️ Search Movies & View Ticket Details
✔️ Update / Cancel Bookings
✔️ Admin Panel for managing users and bookings
✔️ Exception Handling & Validation
✔️ Secure REST APIs with JWT (if implemented)

📂 Project Structure
Movie_Ticket_Management_System/
│
├── ProjectReview_back_end/    # Spring Boot Backend
│   ├── src/                   # Java source code
│   ├── pom.xml                # Maven config
│   └── application.properties # App config
│
└── ProjectReview_frontend/    # Angular Frontend
    ├── src/                   # Angular source code
    ├── package.json           # NPM dependencies
    └── angular.json           # Angular config

⚡ Getting Started
🖥 Backend (Spring Boot)

Go to the backend folder:

cd ProjectReview_back_end


Configure your application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/moviedb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update


Build & run:

mvn spring-boot:run


Backend will start on 👉 http://localhost:8080

🌐 Frontend (Angular)

Go to the frontend folder:

cd ProjectReview_frontend


Install dependencies:

npm install


Run Angular app:

ng serve


Frontend will start on 👉 http://localhost:4200

📸 Screenshots (Optional)

LOGIN SCREEN

<img width="1919" height="1023" alt="image" src="https://github.com/user-attachments/assets/a2a8ddbb-4471-443c-b061-8e0eda383c0e" />

HOME PAGE

<img width="1919" height="1024" alt="image" src="https://github.com/user-attachments/assets/8091b96d-d6cf-4c18-a6c7-9964dce77934" />

TICKET BOOKING PAGE

<img width="1919" height="1019" alt="image" src="https://github.com/user-attachments/assets/15c7c686-810b-4070-991d-eedd77e5d58c" />

DATE-RANGE 

<img width="1919" height="1021" alt="image" src="https://github.com/user-attachments/assets/6ebdc75c-680c-40fc-9cf6-df787e3d5dae" />


