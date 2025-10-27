import java.sql.*;

public class FetchEmployeeData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/nimbusdb";  // replace with your DB name
        String user = "root";                                 // replace with your username
        String password = "password";                         // replace with your password

        try {
            // 1. Load and register the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish connection
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database successfully.");

            // 3. Create a statement
            Statement stmt = con.createStatement();

            // 4. Execute query
            String query = "SELECT EmpID, Name, Salary FROM Employee";
            ResultSet rs = stmt.executeQuery(query);

            // 5. Display records
            System.out.println("\nEmployee Records:");
            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.printf("EmpID: %d | Name: %s | Salary: %.2f%n", id, name, salary);
            }

            // 6. Close resources
            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
