import java.sql.*;
import java.util.Scanner;

class Main {
    private static final String url = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String user = "root";
    private static final String password = "Aman72578800";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Failed to make connection!");
                return;
            }

            int choice;
            do {
                System.out.println("\n===== MENU =====");
                System.out.println("1. Insert student");
                System.out.println("2. View all students");
                System.out.println("3. Update student name");
                System.out.println("4. Delete student by ID");
                System.out.println("5. View students with marks greater than 90");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
//
                        System.out.println("Enter student name:");
                        String name = sc.nextLine();
                        System.out.println("Enter student marks:");
                        int marks = sc.nextInt();
                        System.out.println("Enter student age:");
                        int age = sc.nextInt();

                        String insertQuery = "INSERT INTO students (name, marks, age) VALUES ( ?, ?, ?)";
                        try (PreparedStatement ps = connection.prepareStatement(insertQuery)) {

                            ps.setString(1, name);
                            ps.setInt(2, marks);
                            ps.setInt(3, age);
                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                System.out.println("Student inserted successfully!");
                            } else {
                                System.out.println("Failed to insert student.");
                            }
                        }
                        break;

                    case 2:
                        String selectQuery = "SELECT * FROM students";
                        try (PreparedStatement ps = connection.prepareStatement(selectQuery);
                             ResultSet rs = ps.executeQuery()) {
                            System.out.println("\nStudents in the database:");
                            while (rs.next()) {
                                System.out.println("ID: " + rs.getInt("id") +
                                        ", Name: " + rs.getString("name") +
                                        ", Marks: " + rs.getInt("marks") +
                                        ", Age: " + rs.getInt("age"));
                            }
                        }
                        break;

                    case 3:
                        System.out.println("Enter student ID to update:");
                        int updateId = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter new name:");
                        String newName = sc.nextLine();
                        String updateQuery = "UPDATE students SET name = ? WHERE id = ?";
                        try (PreparedStatement ps = connection.prepareStatement(updateQuery)) {
                            ps.setString(1, newName);
                            ps.setInt(2, updateId);
                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                System.out.println("Student updated successfully!");
                            } else {
                                System.out.println("Failed to update student.");
                            }
                        }
                        break;

                    case 4:
                        System.out.println("Enter student ID to delete:");
                        int deleteId = sc.nextInt();
                        String deleteQuery = "DELETE FROM students WHERE id = ?";
                        try (PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
                            ps.setInt(1, deleteId);
                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                System.out.println("Student deleted successfully!");
                            } else {
                                System.out.println("No student found with the given ID.");
                            }
                        }
                        break;

                    case 5:
                        System.out.println("Student whose number is greater than 90");
                        String highMarksQuery = "Select * from students where marks > 90";
                        try(PreparedStatement ps = connection.prepareStatement(highMarksQuery)) {
                            ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                System.out.println("ID: " + rs.getInt("id") +
                                        ", Name: " + rs.getString("name") +
                                        ", Marks: " + rs.getInt("marks") +
                                        ", Age: " + rs.getInt("age"));
                            }
                        }
                        break;

                    case 6:
                        System.out.println("Exiting program...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }

            } while (choice != 5);

        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        sc.close();
    }
}