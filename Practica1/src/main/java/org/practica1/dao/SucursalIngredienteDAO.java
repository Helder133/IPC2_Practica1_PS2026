package org.practica1.dao;

import org.practica1.config.Conexion;
import org.practica1.models.SucursalIngrediente;
import org.practica1.models.SucursalProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SucursalIngredienteDAO implements CRUD<SucursalIngrediente> {

    private static final String INSERTAR = "INSERT INTO sucursal_ingrediente (sucursal_id, ingrediente_id, estado) VALUES (?,?,?)";
    private static final String ACTUALIZAR_ESTADO = "UPDATE sucursal_ingrediente SET estado = NOT estado WHERE sucursal_id = ? AND ingrediente_id = ?";
    private static final String ELIMINAR = "DELETE FROM sucursal_ingrediente WHERE sucursal_id = ? AND ingrediente_id = ?";
    private static final String EXISTE_INGREDIENTE_SUCURSAL = "SELECT sucursal_id FROM sucursal_ingrediente WHERE sucursal_id = ? AND ingrediente_id = ?";
    private static final String OBTENER_POR_SUCURSAL = "SELECT si.*, i.nombre AS nombre_ingrediente FROM sucursal_ingrediente AS si JOIN ingrediente AS i ON si.ingrediente_id = i.ingrediente_id WHERE si.sucursal_id = ?";


    public boolean existeIngredienteSucursal(int sucursalId, int ingredienteId) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement unique = connection.prepareStatement(EXISTE_INGREDIENTE_SUCURSAL)) {
            unique.setInt(1, sucursalId);
            unique.setInt(2, ingredienteId);
            try (ResultSet resultSet = unique.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public void insertar(SucursalIngrediente entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            insert.setInt(1, entity.getSucursalId());
            insert.setInt(2, entity.getIngredienteId());
            insert.setBoolean(3, entity.isEstado());
            insert.executeUpdate();
        }
    }

    @Override
    public Optional<SucursalIngrediente> obtenerPorId(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<SucursalIngrediente> obtenerTodo() throws SQLException {
        return List.of();
    }

    public List<SucursalIngrediente> obtenerProductoSucursal(int sucursalId) throws SQLException {
        List<SucursalIngrediente> list = new ArrayList<>();
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_POR_SUCURSAL)) {
            select.setInt(1, sucursalId);
            try (ResultSet resultSet = select.executeQuery()) {
                while (resultSet.next()) {
                    SucursalIngrediente sucursalIngrediente = new SucursalIngrediente(resultSet.getInt("sucursal_id"),
                            resultSet.getInt("ingrediente_id"),
                            resultSet.getBoolean("estado"));
                    sucursalIngrediente.setNombreIngrediente(resultSet.getString("nombre_ingrediente"));
                    list.add(sucursalIngrediente);
                }
                return list;
            }
        }
    }

    @Override
    public void actualizar(SucursalIngrediente entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_ESTADO)) {
            update.setInt(1, entity.getSucursalId());
            update.setInt(2, entity.getIngredienteId());
            update.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    public void eliminar(int sucursalId, int ingredienteId) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR)) {
            delete.setInt(1, sucursalId);
            delete.setInt(2, ingredienteId);
            delete.executeUpdate();
        }
    }
}
