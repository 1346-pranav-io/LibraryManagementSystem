import java.sql.*;
import java.util.Scanner;

public class Library {
    private Connection conn;
    private Scanner sc = new Scanner(System.in);

    public Library() {
        conn = DBConnection.getConnection();
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS books (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "title VARCHAR(100), " +
                     "author VARCHAR(100), " +
                     "issued BOOLEAN DEFAULT FALSE)";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBook() {
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.executeUpdate();
            System.out.println("✅ Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void issueBook() {
        System.out.print("Enter Book ID to issue: ");
        int id = sc.nextInt(); sc.nextLine();

        String sql = "UPDATE books SET issued = TRUE WHERE id = ? AND issued = FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("📕 Book issued successfully!");
            else System.out.println("⚠️ Book not available or already issued.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook() {
        System.out.print("Enter Book ID to return: ");
        int id = sc.nextInt(); sc.nextLine();

        String sql = "UPDATE books SET issued = FALSE WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("📗 Book returned successfully!");
            else System.out.println("⚠️ Invalid Book ID.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewBooks() {
        String sql = "SELECT * FROM books";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.printf("%-5s %-30s %-20s %-10s\n", "ID", "Title", "Author", "Status");
            System.out.println("---------------------------------------------------------------");
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("issued"));
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
