package org.practica1.dao;

import org.practica1.models.SucursalProducto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SucursalProductoDAO implements CRUD<SucursalProducto> {
    @Override
    public void insertar(SucursalProducto entity) throws SQLException {

    }

    @Override
    public Optional<SucursalProducto> obtenerPorId(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<SucursalProducto> obtenerTodo() throws SQLException {
        return List.of();
    }

    @Override
    public void actualizar(SucursalProducto entity) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }
}
