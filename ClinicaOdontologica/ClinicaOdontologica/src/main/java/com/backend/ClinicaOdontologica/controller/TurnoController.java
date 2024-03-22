package com.backend.ClinicaOdontologica.controller;

import com.backend.ClinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.ClinicaOdontologica.exceptions.BadRequestException;
import com.backend.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.ClinicaOdontologica.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    //POST
    @PostMapping("/registrar")
    public ResponseEntity<TurnoSalidaDto> registrarPaciente(@RequestBody @Valid TurnoEntradaDto turnoEntradaDto) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(turnoService.registrarTurno(turnoEntradaDto), HttpStatus.CREATED);
    }

}

/*Además de gestionar los pacientes y odontólogos, ahora desde la clínica nos piden que
los pacientes puedan sacar turno para ser atendidos por los odontólogos.
Luego del relevamiento, sabemos que un turno estará compuesto por:
● Id.
● Paciente.
● Odontólogo.
● Fecha y hora.
Nuestro chapter lead para esta etapa nos pide crear la capa de persistencia, de servicio,
y crear la capa de controller para gestionar los turnos desde una API rest.
El sistema deberá permitirnos:
● Dar de alta nuevos turnos, previamente validar que el paciente y el
odontólogo existen en el sistema.
○ Para este punto retorna un código de status 400(BAD REQUEST) en
caso de que no existan el paciente o el odontólogo, en caso de
registrar correctamente el turno, retornar 200.
○ PATH: /turnos METODO: POST
● Listar todos los turnos.
○ PATH:/turnos METODO : GET
● Eliminar turnos.
○ PATH :/turnos/{id} METODO: DELETE
● Actualizar turnos.
○ PATH: /turnos METODO: PUT
● buscar un turno
○ PATH: /turnos/{id} METODO: GET

*/