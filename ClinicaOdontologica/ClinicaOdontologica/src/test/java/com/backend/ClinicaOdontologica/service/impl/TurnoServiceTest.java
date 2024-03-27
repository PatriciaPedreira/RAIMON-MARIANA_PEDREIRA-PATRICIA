package com.backend.ClinicaOdontologica.service.impl;

import com.backend.ClinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.ClinicaOdontologica.exceptions.BadRequestException;
import com.backend.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Test
    void debeRegistrarseTurno_yretornarTurnoSalidaDto() throws BadRequestException {
        // Arrange
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto();

        // Act
        TurnoSalidaDto result = turnoService.registrarTurno(turnoEntradaDto);

        // Assert
        assertNotNull(result);
        // Realizar más aserciones según sea necesario

    }


    @Test
    void listarTurnos_yRetornarListaDeTurnoSalidaDto() {
        // Arrange
        //TurnoService turnoService = new TurnoService(); /* Aquí se pasarían los objetos necesarios para el servicio */

        // Act
        List<TurnoSalidaDto> result = turnoService.listarTurnos();

        // Assert
        assertNotNull(result);
        // Realizar más aserciones según sea necesario
    }

    @Test
    void buscarTurnoPorId_ExistingId_ReturnsTurnoSalidaDto() {
        // Arrange
        Long id = 1L;
        //TurnoService turnoService = new TurnoService();/* Aquí se pasarían los objetos necesarios para el servicio */

        // Act
        TurnoSalidaDto result = turnoService.buscarTurnoPorId(id);

        // Assert
        assertNotNull(result);
        // Realizar más aserciones según sea necesario
    }

    @Test
    void actualizarTurno_ExistingIdAndValidInput_ReturnsUpdatedTurnoSalidaDto() throws ResourceNotFoundException {
        // Arrange
        Long id = 1L;
        //TurnoService turnoService = new TurnoService(); /* Aquí se pasarían los objetos necesarios para el servicio */

        // Act
        TurnoSalidaDto result = turnoService.actualizarTurno(new TurnoEntradaDto(), id);

        // Assert
        assertNotNull(result);
        // Realizar más aserciones según sea necesario
    }

    @Test
    void eliminarTurno_ExistingId_DeletesTurno() throws ResourceNotFoundException {
        // Arrange
        Long id = 1L;
        //TurnoService turnoService = new TurnoService(); /* Aquí se pasarían los objetos necesarios para el servicio */

        // Act
        assertDoesNotThrow(() -> turnoService.eliminarTurno(id));

        // Assert
        // Aquí no hay nada que asercionar ya que el método eliminarTurno debería ejecutarse sin lanzar excepciones
    }
}
//          ATENCION!
//REVISAR Y PENSAR TODOS LOS TEST, SON SOPIADOS Y PEGADOS DEL CHATGPT FALTA ADAPTARLOS Y NOMBRARLOS DE MEJOR MANERA