# Blog_Site Backend

This is a backend for a blog web application built using **React**, **Redux Toolkit**, **Spring Boot**, and **MongoDB**. This app allows users to register, create, and manage their own posts, as well as view posts created by others. The backend is powered by **Spring Boot** and **MongoDB**, and it integrates features such as authentication, CRUD operations, image file handling, and now, AI-powered chat assistance via **Groq AI** using **Spring AI**.

---

## Key Features

- **User Account Creation**: Users can register and create their own accounts.
- **CRUD Operations**: Users can add, update, delete, and view posts. Each post includes details like title, content, and an image, etc.
- **MVC Architecture**: The app follows the **Model-View-Controller (MVC)** architecture for better organization and maintainability.
- **MongoDB**: **MongoDB Atlas** is used as the backend database for storing user data and posts.
- **Lombok**: The project utilizes **Lombok** to minimize boilerplate code by generating getters, setters, and other utility methods.
- **Spring Security**: **Spring Security** is implemented to handle authentication, ensuring that only registered users can access and modify posts.
- **Role-Based Authorization**: The app uses role-based access control, allowing specific user roles to perform authorized actions.
- **Image Handling**: Users can upload images alongside their posts. The backend manages image storage and retrieval.
- **AI Chat Assistant**:
  - Integrated **Groq AI Chat Model** using **Spring AI** for intelligent assistance.
  - RESTful API endpoints connect the frontend chat interface with Groq’s high-performance language model.
  - Users can interact with the chatbot to get writing suggestions, ideas, or answers to general queries directly from the blog interface.

---

## Technologies Used

### Frontend:
- **React**
- **Redux Toolkit**
- **TinyMCE** (for rich text editing)

### Backend:
- **Spring Boot** with Maven
- **MongoDB Atlas** (for database)
- **Spring Security** (for authentication and role-based authorization)
- **Spring AI** (for integrating and communicating with the Groq AI model)
- **Groq API** (for providing fast and smart AI responses)
- **Lombok** (for reducing boilerplate code)

---

## Frontend Repository

👉 [Blog_Site Frontend GitHub](https://github.com/VipinRana08/blog_site)
