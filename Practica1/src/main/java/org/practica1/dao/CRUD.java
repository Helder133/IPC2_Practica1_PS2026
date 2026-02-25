package org.practica1.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUD<T> {
    void insertar(T entity) throws SQLException;
    Optional<T> obtenerPorId (int id) throws SQLException;
    List<T> obtenerTodo () throws SQLException;
    void actualizar(T entity) throws SQLException;
    void eliminar(int id) throws SQLException;
}
