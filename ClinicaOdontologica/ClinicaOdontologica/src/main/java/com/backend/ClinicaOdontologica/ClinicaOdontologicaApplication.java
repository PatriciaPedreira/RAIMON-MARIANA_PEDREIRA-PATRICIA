package com.backend.ClinicaOdontologica;

import com.backend.ClinicaOdontologica.dbconnection.H2Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class ClinicaOdontologicaApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(ClinicaOdontologicaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ClinicaOdontologicaApplication.class, args);
		crearTabla();
		LOGGER.info("ClinicaOdontologicaApplication se está ejecutando...");
	}

	private static void crearTabla(){
		Connection connection = null;
		try {
			connection = H2Connection.getConnection();

		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
	}

}
