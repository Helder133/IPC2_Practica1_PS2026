package org.practica1.dao;

import org.practica1.models.SucursalIngrediente;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SucursalIngredienteDAO implements CRUD<SucursalIngrediente> {
    @Override
    public void insertar(SucursalIngrediente entity) throws SQLException {

    }

    @Override
    public Optional<SucursalIngrediente> obtenerPorId(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<SucursalIngrediente> obtenerTodo() throws SQLException {
        return List.of();
    }

    @Override
    public void actualizar(SucursalIngrediente entity) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }
}
