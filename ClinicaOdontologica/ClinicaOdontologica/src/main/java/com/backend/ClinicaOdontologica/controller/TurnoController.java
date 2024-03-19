package com.backend.ClinicaOdontologica.controller;

public class TurnoController {


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