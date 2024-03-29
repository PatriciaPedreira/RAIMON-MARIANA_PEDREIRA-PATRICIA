package com.backend.ClinicaOdontologica.service.impl;

import com.backend.ClinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.ClinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.ClinicaOdontologica.entity.Odontologo;
import com.backend.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.ClinicaOdontologica.repository.OdontologoRepository;
import com.backend.ClinicaOdontologica.service.IOdontologoService;
import com.backend.ClinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo) {
        LOGGER.info("OdontologoEntradaDto: {}", JsonPrinter.toString(odontologo));
        Odontologo odontologoEntidad = modelMapper.map(odontologo, Odontologo.class);
        Odontologo odontologoEntidadConId = odontologoRepository.save(odontologoEntidad);
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoEntidadConId, OdontologoSalidaDto.class);
        LOGGER.info("Odontologo Registrado: {}", JsonPrinter.toString(odontologoSalidaDto));
        return odontologoSalidaDto;
    }

        @Override
    public List<OdontologoSalidaDto> listarOdontologos() {
        List<OdontologoSalidaDto> odontologosSalidaDto = odontologoRepository.findAll().stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los pacientes: {}", JsonPrinter.toString(odontologosSalidaDto));
        return odontologosSalidaDto;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        OdontologoSalidaDto odontologoEncontrado = null;
        if (odontologoBuscado != null){
            odontologoEncontrado = modelMapper.map(odontologoBuscado, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo encontrado: {}",JsonPrinter.toString(odontologoEncontrado));

        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return odontologoEncontrado;
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id) throws ResourceNotFoundException{
        Odontologo odontologoRecibido = modelMapper.map(odontologoEntradaDto, Odontologo.class);
        Odontologo odontologoAActualizar = odontologoRepository.findById(id).orElse(null);

        OdontologoSalidaDto odontologoSalidaDto;

        if (odontologoAActualizar != null) {

            odontologoAActualizar.setNombre(odontologoRecibido.getNombre());
            odontologoAActualizar.setApellido(odontologoRecibido.getApellido());
            odontologoAActualizar.setMatricula(odontologoRecibido.getMatricula());
            odontologoRepository.save(odontologoAActualizar);

            odontologoSalidaDto = modelMapper.map(odontologoAActualizar, OdontologoSalidaDto.class);

            LOGGER.warn("Odontologo actualizado: {}", JsonPrinter.toString(odontologoSalidaDto));

        } else {
            LOGGER.error("No fue posible actualizar los datos ya que el odontologo no se encuentra registrado");
            throw new ResourceNotFoundException("No es posible actualizar el Odontologo con id " + id + " ya que no se encuentra registrado en nuestra base de datos");
        }

        return odontologoSalidaDto;
    }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        if (buscarOdontologoPorId(id) != null) {
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con id: {}", id);
        } else {
            throw new ResourceNotFoundException("No existe registro del odontologo con id" + id);

        }
    }


}