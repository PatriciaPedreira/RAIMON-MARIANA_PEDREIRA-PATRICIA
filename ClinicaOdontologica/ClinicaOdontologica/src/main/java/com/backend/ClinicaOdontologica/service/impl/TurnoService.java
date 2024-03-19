package com.backend.ClinicaOdontologica.service.impl;

import com.backend.ClinicaOdontologica.repository.TurnoRepository;
import com.backend.ClinicaOdontologica.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);

    private TurnoRepository turnoRepository;
    private ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }



    private void configureMapping() {

    }
}

