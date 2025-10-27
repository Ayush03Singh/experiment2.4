import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/nimbusdb";
    static final String USER = "root";
    static final String PASSWORD = "password";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con.setAutoCommit(false); // Transaction Handling

            int choice;
            do {
                System.out.println("\n=== Product Management Menu ===");
                System.out.println("1. Insert Product");
                System.out.println("2. View All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        insertProduct(con, sc);
                        break;
                    case 2:
                        viewProducts(con);
                        break;
                    case 3:
                        updateProduct(con, sc);
                        break;
                    case 4:
                        deleteProduct(con, sc);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } while (choice != 5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void insertProduct(Connection con, Scanner sc) {
        try {
            String sql = "INSERT INTO Product(ProductID, ProductName, Price, Quantity) VALUES(?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            System.out.print("Enter ProductID: ");
            ps.setInt(1, sc.nextInt());
            System.out.print("Enter Product Name: ");
            ps.setString(2, sc.next());
            System.out.print("Enter Price: ");
            ps.setDouble(3, sc.nextDouble());
            System.out.print("Enter Quantity: ");
            ps.setInt(4, sc.nextInt());

            ps.executeUpdate();
            con.commit();
            System.out.println("Product added successfully.");
            ps.close();
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    static void viewProducts(Connection con) throws SQLException {
        String sql = "SELECT * FROM Product";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("\n--- Product List ---");
        while (rs.next()) {
            System.out.printf("ID: %d | Name: %s | Price: %.2f | Qty: %d%n",
                    rs.getInt("ProductID"), rs.getString("ProductName"),
                    rs.getDouble("Price"), rs.getInt("Quantity"));
        }
        rs.close();
        stmt.close();
    }

    static void updateProduct(Connection con, Scanner sc) {
        try {
            String sql = "UPDATE Product SET ProductName=?, Price=?, Quantity=? WHERE ProductID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            System.out.print("Enter ProductID to update: ");
            ps.setInt(4, sc.nextInt());
            System.out.print("Enter new Product Name: ");
            ps.setString(1, sc.next());
            System.out.print("Enter new Price: ");
            ps.setDouble(2, sc.nextDouble());
            System.out.print("Enter new Quantity: ");
            ps.setInt(3, sc.nextInt());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                con.commit();
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Product not found.");
            }
            ps.close();
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    static void deleteProduct(Connection con, Scanner sc) {
        try {
            String sql = "DELETE FROM Product WHERE ProductID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            System.out.print("Enter ProductID to delete: ");
            ps.setInt(1, sc.nextInt());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                con.commit();
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found.");
            }
            ps.close();
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
