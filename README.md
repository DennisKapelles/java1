# Staff Evaluation System – Database Project

## 📌 Project Overview

This project focuses on designing and implementing a **relational database** that supports a **staff evaluation system** for a group of companies. It is developed in Java using JDBC for interaction with the database and includes multiple graphical user interfaces (GUIs) for different user roles.

## 🧩 Objective

- Familiarize students with database design, SQL (queries, triggers, stored procedures), and GUI implementation using Java.
- Extend a given database schema to support evaluation logic, job applications, logging, and user roles.
- Simulate real-world functionality with assumptions where necessary.

---

## 👥 User Roles

The system supports 4 main user roles:

1. **Manager** – Manages company data, employee folders, and initiates promotions.
2. **Evaluator** – Posts job openings and evaluates candidates across three phases.
3. **Employee** – Applies for available promotion jobs and views their evaluation results.
4. **Administrator** – Manages user accounts, companies, and system objects.

---

## 🗃️ Key Database Features

- Supports multiple companies.
- Stores user credentials, company info, and user profiles.
- Tracks employee data, including CVs, degrees, certifications, projects, and awards.
- Logs insert, update, and delete actions by users in a dedicated audit table.

---

## ⚙️ Functional Requirements

- **Evaluation process** involves three phases:
  1. Interview (0–4)
  2. Manager report (0–4)
  3. Academic/professional background (0–2)

- Final evaluation results are automatically calculated and stored.
- **Applications system** allows employees to apply for promotions.
- **Logging system** records all data modifications.

---

## 🧮 SQL Requirements

- Schema extension
- `CREATE` and `INSERT` scripts for testing
- Stored procedures for:
  - Fetching evaluations for an employee
  - Finalizing evaluation results
  - Displaying ranked candidates
- Triggers for:
  - Logging changes
  - Enforcing data integrity (e.g., preventing username changes)

---

## 🖥️ GUI Interfaces (Java with JDBC)

Each user has a specific interface:

- **Login screen** for all users
- **Manager GUI**: Edit company data, view evaluations, manage employees
- **Evaluator GUI**: Post jobs, enter evaluations, run stored procedures
- **Employee GUI**: Submit applications, update profile, view status
- **Admin GUI**: Manage users, companies, and objects

GUIs use dropdowns and list-based inputs to minimize errors and improve usability.

---

## 📝 Notes

- Final evaluation is locked once submitted.
- Users cannot change their usernames.
- GUI access and permissions are restricted based on user role.
