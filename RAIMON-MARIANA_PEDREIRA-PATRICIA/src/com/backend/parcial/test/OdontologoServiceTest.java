package com.backend.parcial.test;

import com.backend.parcial.dao.impl.OdontologoDaoMemoria;
import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.service.OdontologoService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OdontologoServiceTest {

    private OdontologoService odontologoService;
    @Test
    void debeRegistrarUnOdontologoEnMemoria(){

        odontologoService = new OdontologoService(new OdontologoDaoMemoria(new ArrayList<>()));

        Odontologo odontologo = new Odontologo("123", "Valeria","Gomez");

        Odontologo odontologoGuardado = odontologoService.registrar(odontologo);

        assertNotNull(odontologoGuardado);

    }

    @Test
    void debeListarOdontologoEnMemoria(){

        odontologoService = new OdontologoService(new OdontologoDaoMemoria(new ArrayList<>()));

        Odontologo odontologo = new Odontologo("123", "Valeria","Gomez");
        Odontologo odontologo2 = new Odontologo("1234", "Pepe","Pereira");
        Odontologo odontologo3 = new Odontologo("1235", "Jose","Rodriguez");

        odontologoService.registrar(odontologo);
        odontologoService.registrar(odontologo2);
        odontologoService.registrar(odontologo3);

        List<Odontologo> odontologosListados= odontologoService.listarOdontologo();

        assertNotNull(odontologosListados);
        assertEquals(3,odontologosListados.size());
    }




}
