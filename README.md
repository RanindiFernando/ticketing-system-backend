# TicketingSystem
 Backend of Real-Time Ticketing System

## Table of Contents
1. [Project Overview](#project-overview)
2. [Technologies Used](#technologies-used)
3. [Setup Instructions](#setup-instructions)
4. [Usage Instructions](#usage-instructions)
  
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
git clone: https://github.com/RanindiFernando/TicketingSystemBackend.git
### Prerequisites
1. **Java SDK 17**: Ensure that you have Java SDK 17 installed on your machine.
   - To check if Java is installed, run: 
     ```bash
     java -version
     ```
   - If Java is not installed, download and install it from the official [Java website](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

2. **MySQL Database**: Set up a MySQL instance and create a database for the ticketing system.

## Usage Instructions
### Configuration and Starting the Simulation
1. Set Vendor and Customer Totals:
Use the /setVendorTotal and /setCustomerTotal endpoints to configure the number of vendors and customers.

2. Configure Simulation Parameters:
Use the /start endpoint with the following payload:
{
  "ticketReleaseRate": 1000,
  "ticketRetrievalRate": 2000,
  "maxTicketCapacity": 100
}

3. Start the Simulation:
Call the /start endpoint to begin the ticketing simulation.

4. Stop the Simulation:
Use the /stop endpoint to terminate the simulation.

### Viewing Logs and Transactions
Retrieve Remaining Tickets:
Access the /tickets endpoint to check the number of tickets available in the pool.

View Transaction Logs:
Use the /transactions endpoint to retrieve transaction logs stored in transaction_logs.json.


