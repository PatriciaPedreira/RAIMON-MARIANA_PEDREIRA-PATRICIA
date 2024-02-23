package com.backend.parcial.test;

import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.service.OdontologoService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OdontologoServiceTest {

    private OdontologoService odontologoService;
    @Test
    void debeRegistrarOdontologosYMostrarTodaLaLista(){

        //Guardamos previamente algunos Odontologos para la prueba.
        List<Odontologo> odontologos = new ArrayList<>();
        odontologos.add(new Odontologo("123", "Virginia", "Lopez"));
        odontologos.add(new Odontologo("456", "Carlos", "Rodriguez"));
        odontologos.add(new Odontologo("789", "Gonzalo", "Gonzalez"));
        odontologos.add(new Odontologo("101", "Maria", "Saravia"));

        //Obtenemos la lista de los Odontologos.
        odontologos = odontologoService.listarOdontologo();

        //Verificamos que la lista tenga los Odontologos registrados.
        assertEquals(4, odontologos.size());
    }


}
