import java.sql.*;
import java.util.Scanner;

public class Banking {

    /**
     * setup the database connection parameters
     */
    private static final String url = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String user = "root";
    private static final String password = "Aman72578800";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        /**
         * Setup the JDBC driver
         */
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }

        /**
         * Establish the connection to the database
         */
        try (Connection cconnection = DriverManager.getConnection(url, user, password)) {
            if (cconnection != null) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Failed to make connection!");
                return;
            }

            int choice;
            do {
                System.out.println("\n===== MENU =====");
                System.out.println("1. Insert Patients");
                System.out.println("2. View all Patients");
                System.out.println("3. Update Patient Details");
                System.out.println("4. Delete Patient by ID");
                System.out.println("5. Search Patient by Name");
                System.out.println("6 List all Admit Patients ");
                System.out.println("7. List all Discharge Patients ");
                System.out.println("8. Count all Patients admitted and discharged");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        String sqlInsert = "INSERT INTO patients (name,age,disease,admission_date,discharged) VALUES (?,?,?,?,?)";
                        try (PreparedStatement ps = cconnection.prepareStatement(sqlInsert)) {
                            System.out.println("Enter Patient Name:");
                            String name = sc.nextLine();
                            System.out.println("Enter Patient Age:");
                            int age = sc.nextInt();
                            sc.nextLine(); // Consume newline
                            System.out.println("Enter Patient Disease:");
                            String disease = sc.nextLine();
                            System.out.println("Enter Admission Date (YYYY-MM-DD):");
                            String admissionDate = sc.nextLine();
                            System.out.println("Is the patient discharged? (true/false):");
                            boolean discharged = sc.nextBoolean();

                            ps.setString(1, name);
                            ps.setInt(2, age);
                            ps.setString(3, disease);
                            ps.setString(4, admissionDate);
                            ps.setBoolean(5, discharged);

                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                System.out.println("Patient inserted successfully!");
                            } else {
                                System.out.println("Failed to insert patient.");
                            }
                        } catch (Exception e) {
                            System.out.println("Error inserting patient: " + e.getMessage());
                        }
                        break;

                    case 2:
                        String sqlSelect = "SELECT * FROM patients";
                        try (PreparedStatement ps = cconnection.prepareStatement(sqlSelect);
                             ResultSet rs = ps.executeQuery()) {
                            System.out.println("\nPatients in the database:");
                            while (rs.next()) {
                                System.out.println("ID: " + rs.getInt("id") +
                                        ", Name: " + rs.getString("name") +
                                        ", Age: " + rs.getInt("age") +
                                        ", Disease: " + rs.getString("disease") +
                                        ", Admission Date: " + rs.getString("admission_date") +
                                        ", Discharged: " + rs.getBoolean("discharged"));
                            }
                        } catch (Exception e) {
                            System.out.println("Error retrieving patients: " + e.getMessage());
                        }
                        break;

                    case 3:
                        String sqlUpdate = "UPDATE patients SET name = ?, age = ?, disease = ?, discharged = ? WHERE id = ?";
                        try (PreparedStatement ps = cconnection.prepareStatement(sqlUpdate)) {
                            System.out.println("Enter Patient ID to update:");
                            int updateId = sc.nextInt();
                            sc.nextLine(); // Consume newline
                            System.out.println("Enter new Patient Name:");
                            String newName = sc.nextLine();
                            System.out.println("Enter new Patient Age:");
                            int newAge = sc.nextInt();
                            sc.nextLine(); // Consume newline
                            System.out.println("Enter new Patient Disease:");
                            String newDisease = sc.nextLine();
                            System.out.println("Enter new Admission Date (YYYY-MM-DD):");
                            String newAdmissionDate = sc.nextLine();

                            ps.setString(1, newName);
                            ps.setInt(2, newAge);
                            ps.setString(3, newDisease);
                            ps.setString(4, newAdmissionDate);
                            ps.setInt(5, updateId);

                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                System.out.println("Patient updated successfully!");
                            } else {
                                System.out.println("Failed to update patient.");
                            }
                        } catch (Exception e) {
                            System.out.println("Error updating patient: " + e.getMessage());
                        }

                        break;

                    case 4:
                        String sqlDelete = "DELETE FROM patients WHERE id = ?";
                        try (PreparedStatement ps = cconnection.prepareStatement(sqlDelete)) {
                            System.out.println("Enter Patient ID to delete:");
                            int deleteId = sc.nextInt();
                            ps.setInt(1, deleteId);

                            int rows = ps.executeUpdate();
                            if (rows > 0) {
                                System.out.println("Patient deleted successfully!");
                            } else {
                                System.out.println("Failed to delete patient.");
                            }
                        } catch (Exception e) {
                            System.out.println("Error deleting patient: " + e.getMessage());
                        }
                        break;

                    case 5:
                        String sqlSearch = "SELECT * FROM patients WHERE name LIKE ?";
                        try (PreparedStatement ps = cconnection.prepareStatement(sqlSearch)) {
                            System.out.println("Enter Patient Name to search:");
                            String searchName = sc.nextLine();
                            ps.setString(1, "%" + searchName + "%");

                            try (ResultSet rs = ps.executeQuery()) {
                                System.out.println("\nSearch Results:");
                                while (rs.next()) {
                                    System.out.println("ID: " + rs.getInt("id") +
                                            ", Name: " + rs.getString("name") +
                                            ", Age: " + rs.getInt("age") +
                                            ", Disease: " + rs.getString("disease") +
                                            ", Admission Date: " + rs.getString("admission_date") +
                                            ", Discharged: " + rs.getBoolean("discharged"));
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error searching patient: " + e.getMessage());
                        }
                        break;

                    case 6:
                        String sqlAdmit = "SELECT * FROM patients WHERE discharged = false";
                        try (PreparedStatement ps = cconnection.prepareStatement(sqlAdmit);
                             ResultSet rs = ps.executeQuery()) {
                            System.out.println("\nAdmitted Patients:");
                            while (rs.next()) {
                                System.out.println("ID: " + rs.getInt("id") +
                                        ", Name: " + rs.getString("name") +
                                        ", Age: " + rs.getInt("age") +
                                        ", Disease: " + rs.getString("disease") +
                                        ", Admission Date: " + rs.getString("admission_date"));
                            }
                        } catch (Exception e) {
                            System.out.println("Error retrieving admitted patients: " + e.getMessage());
                        }
                        break;

                    case 7:
                        String sqlDischarge = "SELECT * FROM patients WHERE discharged = true";
                        try (PreparedStatement ps = cconnection.prepareStatement(sqlDischarge);
                             ResultSet rs = ps.executeQuery()) {
                            System.out.println("\nDischarged Patients:");
                            while (rs.next()) {
                                System.out.println("ID: " + rs.getInt("id") +
                                        ", Name: " + rs.getString("name") +
                                        ", Age: " + rs.getInt("age") +
                                        ", Disease: " + rs.getString("disease") +
                                        ", Admission Date: " + rs.getString("admission_date"));
                            }
                        } catch (Exception e) {
                            System.out.println("Error retrieving discharged patients: " + e.getMessage());
                        }
                        break;

                    case 8:
                        String sqlCount = "SELECT COUNT(*) AS total_patients, " +
                                "(SELECT COUNT(*) FROM patients WHERE discharged = false) AS admitted, " +
                                "(SELECT COUNT(*) FROM patients WHERE discharged = true) AS discharged FROM patients";
                        try (PreparedStatement ps = cconnection.prepareStatement(sqlCount);
                             ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                int totalPatients = rs.getInt("total_patients");
                                int admittedPatients = rs.getInt("admitted");
                                int dischargedPatients = rs.getInt("discharged");
                                System.out.println("Total Patients: " + totalPatients);
                                System.out.println("Admitted Patients: " + admittedPatients);
                                System.out.println("Discharged Patients: " + dischargedPatients);
                            }
                        } catch (Exception e) {
                            System.out.println("Error counting patients: " + e.getMessage());
                        }
                        break;

                }
            } while (choice != 9);

        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
