package com.backend.parcial;
        import com.backend.parcial.dbconnection.H2Connection;
        import java.sql.Connection;
        import java.sql.DriverManager;

public class Application {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = H2Connection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}