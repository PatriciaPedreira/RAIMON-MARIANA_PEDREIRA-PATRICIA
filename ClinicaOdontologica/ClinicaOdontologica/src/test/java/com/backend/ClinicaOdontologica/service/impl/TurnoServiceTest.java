package com.backend.ClinicaOdontologica.service.impl;

import com.backend.ClinicaOdontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.ClinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.ClinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.ClinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.ClinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.ClinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.ClinicaOdontologica.exceptions.BadRequestException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void debeRegistrarseTurnoConPacienteCarla_yOdontologoMaria_yRetornarSuId() throws BadRequestException {
        // Arrange
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Carla", "Perez", 123456789,
                LocalDate.now() , new DomicilioEntradaDto("Gualeguay", 3634, "Montevideo", "Montevideo"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("9988", "María", "González");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto();
        turnoEntradaDto.setPacienteId(pacienteSalidaDto.getId());
        turnoEntradaDto.setOdontologoId(odontologoSalidaDto.getId());
        turnoEntradaDto.setFechaYHora(LocalDateTime.now());
        // Act
        TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

        assertNotNull(turnoSalidaDto);
        assertNotNull(turnoSalidaDto.getId());
        assertEquals(1, turnoSalidaDto.getId());
    }

    @Test
    @Order(2)
    void buscarTurnoPorElId1_RetornarTurnoSalidaDto() {
        // Arrange
        Long id = 1L;

        // Act
        TurnoSalidaDto turnoSalidaDto = turnoService.buscarTurnoPorId(id);

        // Assert
        assertNotNull(turnoSalidaDto.getId());
        assertEquals(1L, turnoSalidaDto.getId());

    }

    @Test
    @Order(3)
    void listarTurnos_yRetornarListaDeTurnoSalidaDto() {

        // Act
        List<TurnoSalidaDto> turnos = turnoService.listarTurnos();

        // Assert
        assertNotNull(turnos);
        assertEquals(1L, turnos.size());

    }
}