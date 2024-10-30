import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "abcd");
            System.out.println("Connected succcessfully to the db:");

        } catch (SQLException ex) {
            System.out.println("Exception in db:" + ex.getMessage());
        }
    }
}
