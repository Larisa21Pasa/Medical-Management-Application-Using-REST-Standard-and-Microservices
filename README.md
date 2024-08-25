# Medical-Management-Application-Using-REST-Standard-and-Microservices
## Project Description
This repository hosts a RESTful microservice-based application designed for managing medical records and services within a healthcare center. The project was developed as part of the "Service-Oriented Programming" course, with the goal of integrating various components explored during laboratory sessions into a cohesive software solution.

## Project Overview
The application is tailored to serve a single healthcare center, providing services across multiple medical specializations. It enables doctors to monitor patient health effectively and manage consultations, while allowing patients to interact seamlessly with the healthcare center for booking appointments and receiving care.

## Core Functionality
The application is composed of several microservices, each responsible for specific aspects of the system:

## Patient and Doctor Management (SQL-based Microservice):
This microservice manages patient data, doctor information, and appointment scheduling. It ensures that appointments are conflict-free, enforcing rules such as preventing patients from booking multiple appointments with the same doctor on the same day and maintaining a strict 15-minute duration for each consultation.

## Consultation and Medical Details Management (MongoDB-based Microservice):
This microservice stores and manages consultation records and any additional investigations. By leveraging MongoDB, it effectively handles dynamic and complex data structures involved in medical consultations.

## Gateway Service:
To facilitate seamless communication between the SQL and MongoDB microservices, a gateway service acts as an intermediary. It routes requests between services and ensures data consistency across the system.

## Authentication and Authorization (gRPC-based Microservice):
Security is a critical aspect of this application. The authentication and authorization processes are managed by a dedicated microservice built with Spring Boot, using gRPC for communication with the patient and consultation microservices. This approach allows for secure and efficient communication, bypassing the gateway for authentication data, which enhances security and reduces latency.

## Importance of gRPC Integration
gRPC is employed between the authentication service and the patient/consultation microservices to enable direct, high-performance communication. This approach avoids routing sensitive authentication data through the gateway, thereby enhancing security and efficiency. gRPC also allows for robust, type-safe communication, which is particularly valuable in ensuring the reliability of the authentication processes.

## RESTful Standards and Database Interactions
The application adheres to RESTful principles, ensuring that each HTTP request is handled according to standardized codes and methods. This includes the proper use of status codes (e.g., 200 OK, 404 Not Found, 400 Bad Request) and enforcing strict validation rules for incoming requests. For example, the system prevents writing or updating database records if the request URL contains incorrect or empty query parameters, thereby ensuring data integrity and avoiding unnecessary processing.

By adhering to RESTful standards, the application ensures that all interactions between the client and server are predictable, reliable, and secure, allowing for easier maintenance and scalability. This strict adherence to REST principles also facilitates better error handling and debugging, as well as ensuring a consistent approach to managing resources across the different microservices.

## Technical Requirements and Constraints
Single Medical Center: The application is designed for a single healthcare facility, supporting multiple medical specializations.

Doctor Specialization: Each doctor is associated with only one medical specialty.

Appointment Scheduling: Patients can book appointments with different doctors across various specialties, with enforced rules to prevent conflicts. Appointments can only be scheduled for future dates or at least 15 minutes ahead of the current time.
