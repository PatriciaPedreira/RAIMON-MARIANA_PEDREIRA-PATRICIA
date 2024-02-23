package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.dbconnection.H2Connection;
import com.backend.parcial.entity.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {

    private Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoRegistrado = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ODONTOLOGOS (NUMMATRICULA, NOMBRE, APELLIDO) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getNumMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());

            preparedStatement.execute();

            odontologoRegistrado = new Odontologo(odontologo.getNumMatricula(), odontologo.getNombre(), odontologo.getApellido());

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()) {
                odontologoRegistrado.setId(resultSet.getInt("id"));
            }

            connection.commit();

            LOGGER.info("Se registró el odontólogo: " + odontologoRegistrado);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Se detectó un problema");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } catch (SQLException exception) {
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                LOGGER.error("No se logró cerrar con éxito la conexión: " + ex.getMessage());
            }
        }

        return odontologoRegistrado;
    }

    @Override
    public List<Odontologo> listarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ODONTOLOGOS");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Odontologo odontologo = new Odontologo(resultSet.getString("NUMMATRICULA"), resultSet.getString("NOMBRE"), resultSet.getString("APELLIDO"));
                odontologo.setId(resultSet.getInt("id"));
                odontologos.add(odontologo);
            }

            LOGGER.info("Listado de todos los odontólogos: " + odontologos);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                LOGGER.error("Ocurrió un error al intentar cerrar la Base de Datos. " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        return odontologos;
    }
}
