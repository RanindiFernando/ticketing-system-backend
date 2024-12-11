# TicketingSystem
 Backend of Real-Time Ticketing System

## Table of Contents
1. [Project Overview](#project-overview)
2. [Technologies Used](#technologies-used)
3. [Setup Instructions](#setup-instructions)
4. [Class Diagram](#class-diagram)
  
## Project Overview
The Ticketing System Backend is a Spring Boot-based application designed to simulate a ticketing system. It allows vendors to add tickets to a shared pool and customers to purchase tickets in real-time. The simulation operates in a multi-threaded environment, ensuring concurrency and efficient ticket management.

Key features include:

- Simulation Management: Multi-threaded vendor and customer interactions.
- Ticket Pool Management: Add and retrieve tickets in a thread-safe manner.
- Transaction Logging: Records transactions in JSON format for audit and analysis.
- RESTful API: Provides endpoints for configuration, simulation control, and status updates.
This application integrates MySQL for data persistence, Gson for JSON serialization, and uses Spring Boot to handle backend operations.

## Technologies Used
- **Java SDK 17**: Application programming language.
- **Spring Boot 3.3.5**: Framework for creating RESTful APIs.
- **MySQ**L: Database for storing ticket records.
- **Gson**: Library for JSON serialization/deserialization.
- **Lombok**: Reduces boilerplate code by generating getters, setters, and constructors.

## Setup Instructions
### Prerequisites
1. **Java SDK 17**: Ensure that you have Java SDK 17 installed on your machine.
   - To check if Java is installed, run: 
     ```bash
     java -version
     ```
   - If Java is not installed, download and install it from the official [Java website](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

2. **MySQL Database**: Set up a MySQL instance and create a database for the ticketing system.

