package com.backend.ClinicaOdontologica.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Connection {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver"); //indicar que Driver vamos a usar
        return DriverManager.getConnection("jdbc:h2:~/clinica;INIT=RUNSCRIPT FROM 'create.sql'", "mapa", "mapa");
    }
}
