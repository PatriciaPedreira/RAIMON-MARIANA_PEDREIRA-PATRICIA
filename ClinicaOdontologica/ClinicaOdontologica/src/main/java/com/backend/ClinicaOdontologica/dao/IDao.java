package com.backend.ClinicaOdontologica.dao;

import java.util.List;

public interface IDao<T> {
    T registrar(T t);

    T buscarPorId(int id);

    List<T> listarTodos();
}
