package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.dbconnection.H2Connection;
import com.backend.parcial.entity.Odontologo;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoMemoria implements IDao<Odontologo> {

    private Logger LOGGER = Logger.getLogger(OdontologoDaoMemoria.class);
    private List<Odontologo> odontologoList;
    public OdontologoDaoMemoria(List<Odontologo> odontologoList){
        this.odontologoList = odontologoList;

    }

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        int id = odontologoList.size() + 1;
        Odontologo odontologoGuardado = new Odontologo(id, odontologo.getNumMatricula(), odontologo.getNombre(), odontologo.getApellido());
        odontologoList.add(odontologo);
        LOGGER.info("Odontologo guardado: " + odontologoGuardado);
        return odontologoGuardado;
    }

    @Override
    public List<Odontologo> listarTodos() {
        return odontologoList;
    }
}