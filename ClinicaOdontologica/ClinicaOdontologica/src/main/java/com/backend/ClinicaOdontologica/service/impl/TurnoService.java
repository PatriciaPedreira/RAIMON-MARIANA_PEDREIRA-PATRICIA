package com.backend.ClinicaOdontologica.service.impl;

import com.backend.ClinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.ClinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.ClinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.ClinicaOdontologica.entity.Odontologo;
import com.backend.ClinicaOdontologica.entity.Paciente;
import com.backend.ClinicaOdontologica.entity.Turno;
import com.backend.ClinicaOdontologica.exceptions.BadRequestException;
import com.backend.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.ClinicaOdontologica.repository.TurnoRepository;
import com.backend.ClinicaOdontologica.service.ITurnoService;
import com.backend.ClinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final ModelMapper modelMapper;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;
    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper,
                        PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException {
        TurnoSalidaDto turnoSalidaDto;
        PacienteSalidaDto paciente = pacienteService.buscarPacientePorId(turnoEntradaDto.getPacienteId());
        OdontologoSalidaDto odontologo = odontologoService.buscarOdontologoPorId(turnoEntradaDto.getOdontologoId());

        String pacienteNoEnBdd = "El paciente no se encuentra en nuestra base de datos";
        String odontologoNoEnBdd = "El odontologo no se encuentra en nuestra base de datos";
        String ambosNulos = "El paciente y el odontologo no se encuentran en nuestra base de datos";

        if (paciente == null || odontologo == null) {
            if (paciente == null && odontologo == null) {
                LOGGER.error(ambosNulos);
                throw new BadRequestException(ambosNulos);
            } else if (paciente == null) {
                LOGGER.error(pacienteNoEnBdd);
                throw new BadRequestException(pacienteNoEnBdd);
            } else {
                LOGGER.error(odontologoNoEnBdd);
                throw new BadRequestException(odontologoNoEnBdd);
            }
        } else {
            Turno turnoNuevo = new Turno(modelMapper.map(odontologo, Odontologo.class),modelMapper.map(paciente, Paciente.class), turnoEntradaDto.getFechaYHora());
            turnoNuevo = turnoRepository.save(turnoNuevo);
            turnoSalidaDto = entidadADtoSalida(turnoNuevo, paciente, odontologo);
            LOGGER.info("Nuevo turno registrado con exito: {}", JsonPrinter.toString(turnoSalidaDto));
        }
        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnoSalidaDtos =  turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();

        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnoSalidaDtos));
        return turnoSalidaDtos;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if(turnoBuscado != null){
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
        }

        return turnoEncontrado;
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) throws ResourceNotFoundException{
    TurnoSalidaDto turnoSalidaDto;
        Turno turnoExistente = turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con el ID: " + id));

        PacienteSalidaDto paciente = pacienteService.buscarPacientePorId(turnoEntradaDto.getPacienteId());
        OdontologoSalidaDto odontologo = odontologoService.buscarOdontologoPorId(turnoEntradaDto.getOdontologoId());

        String pacienteNoExiste = "No se encontró el paciente con ID: " + turnoEntradaDto.getPacienteId();
        String odontologoNoExiste = "No se encontró el odontólogo con ID: " + turnoEntradaDto.getOdontologoId();

        if(paciente == null || odontologo == null){
            if (paciente == null) {
                LOGGER.error(pacienteNoExiste);
                throw new ResourceNotFoundException(pacienteNoExiste);
            } else {
                LOGGER.error(odontologoNoExiste);
                throw new ResourceNotFoundException(odontologoNoExiste);
            }
        } else {
            //Actualizamos los datos del turno con la información del DTO de entrada
            turnoExistente.setPaciente(modelMapper.map(paciente, Paciente.class));
            turnoExistente.setOdontologo(modelMapper.map(odontologo, Odontologo.class));
            turnoExistente.setFechaYHora(turnoEntradaDto.getFechaYHora());
            //Guardar y el turno actualizado
            Turno turnoActualizado = turnoRepository.save(turnoExistente);
            //Convertir el turno actualizado a un DTO de salida y devolverlo
            turnoSalidaDto = entidadADtoSalida(turnoActualizado, paciente, odontologo);

            LOGGER.info("Turno actualizado con éxito: {}", JsonPrinter.toString(turnoActualizado));
        }
        return turnoSalidaDto;

    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if (buscarTurnoPorId(id) != null){
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el Turno con id {}", id);

        }else
            throw new ResourceNotFoundException("No existe registro del Turno con id " + id);

    }

    private TurnoSalidaDto entidadADtoSalida(Turno turno, PacienteSalidaDto pacienteSalidaDto, OdontologoSalidaDto odontologoSalidaDto){
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turno, TurnoSalidaDto.class);
        turnoSalidaDto.setPacienteSalidaDto(pacienteSalidaDto);
        turnoSalidaDto.setOdontologoSalidaDto(odontologoSalidaDto);
        return turnoSalidaDto;
    }

}