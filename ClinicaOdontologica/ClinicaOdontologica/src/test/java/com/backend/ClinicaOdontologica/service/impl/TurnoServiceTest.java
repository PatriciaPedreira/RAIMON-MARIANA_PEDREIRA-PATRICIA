package com.backend.ClinicaOdontologica.service.impl;

import com.backend.ClinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.ClinicaOdontologica.exceptions.BadRequestException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Test
    @Order(1)
    void debeRegistrarseTurno_retornarEnTurnoSalidaDtoSuId_yLanzarExcepcionAlIntentarRegistrarloNuevamente(){
        // Arrange
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(1L,1L, LocalDateTime.of(2024,3,31,13,30,00));

        // Act y Assert
        assertDoesNotThrow(() -> {
            TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);
            assertNotNull(turnoSalidaDto);
            assertNotNull(turnoSalidaDto.getId());
            assertEquals(1L, turnoSalidaDto.getId());
        });

        // Verifica que lanza la BadRequestException al intentar registrar el mismo turno nuevamente
        assertThrows(BadRequestException.class, () -> turnoService.registrarTurno(turnoEntradaDto));
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