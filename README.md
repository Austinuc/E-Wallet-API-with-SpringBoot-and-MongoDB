# E-Wallet Backend Microservice:  iWallet

This is a Spring Boot based digital wallet application that uses MongoDB as the database. 
The application allows users to create and manage their digital wallet, add funds to it, and make transactions.

`Built with Spring Boot, secured with Spring Security (JWT), documented with Swagger (API),
containerized with Docker, deployed on an AWS EC2 instance.`

### Microservices components
* BackendServices
* FrontendServices

### Project Setup
    Language: Java
    Build system: Maven
    JDK version: 11
    Spring boot version: 2.76
    Memcached
    MongoDB

### Installation

* Ensure Memcached is installed and running on your machine before you run this service.
* **Clone the repository:** git clone https://github.com/Austinuc/E-Wallet-API-with-SpringBoot-and-MongoDB.git
* **Build the project using maven:** mvn clean install 
* **Run the application:** mvn spring-boot:run 

### Usage
The application exposes a RESTful API for creating and managing digital wallets. 
Use the following link to see access the exposed API doc when the application is running on your local machine:
http://localhost:8083/swagger-ui/index.html

### Authentication and Authorization
Uses Spring Security with JWT for stateless authentication and authorization.

### Configuration
The application uses MongoDB as the database. The server is configured to run on port 8083 which can be
changed in the application.properties file.

The database, email and all configurations can be set in the application.properties file.

### Note
For security purpose, please make sure to set appropriate access controls for MongoDB.

### Deployment
The application can be deployed on any Java Servlet container, or docker containers.

### Support
For any issues or queries, please raise a ticket on the GitHub repository or email me on austin5astro@gmail.com.