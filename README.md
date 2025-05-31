# 🎓 JDBC Student Management System

This is a simple **Java CLI application** that performs **CRUD operations** on a `students` table using **JDBC** (Java Database Connectivity) with **MySQL**. You can insert, view, update, and delete student records through a text-based menu.

---

## 🧠 Features

- ✅ Insert new student data
- 📄 View all students
- ✏️ Update a student's name by ID
- ❌ Delete a student by ID
- 📊 View students with marks greater than 90
- 🚪 Exit the program safely

---

## 🛠 Technologies Used

- Java
- JDBC (Java Database Connectivity)
- MySQL
- IntelliJ IDEA (Optional, or any other IDE)

---

## 🧾 Prerequisites

- Java installed (JDK 8+)
- MySQL Server running
- MySQL JDBC Driver (e.g., `mysql-connector-j`)
- Basic understanding of Java and SQL

---

## 🧱 Database Setup

Run the following SQL to create the database and table:

```sql
CREATE DATABASE mydatabase;

USE mydatabase;

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    marks INT NOT NULL,
    age INT NOT NULL
);


🚀 How to Run
Clone the repository or copy the Main.java file.
Add the MySQL JDBC driver to your classpath.
Update your DB credentials in the code:

```
private static final String url = "jdbc:mysql://localhost:3306/mydatabase";
private static final String user = "root";
private static final String password = "YOUR_PASSWORD_HERE";

```

Compile and run the Java file:
```
javac Main.java
java Main
```

## 📖 Sample Output
```
===== MENU =====
1. Insert student
2. View all students
3. Update student name
4. Delete student by ID
5. View students with marks greater than 90
6. Exit
   Enter your choice:
```


💡 Improvements for the Future
Input validation
Add email or contact field
GUI version using JavaFX or Swing
Pagination for large result sets

🤝 Author
Deepak Kumar Singh
## 📄 License
This project is open-source and free to use.