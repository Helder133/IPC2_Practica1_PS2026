package org.practica1.dao;

import org.practica1.config.Conexion;
import org.practica1.models.SucursalProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SucursalProductoDAO implements CRUD<SucursalProducto> {

    private static final String INSERTAR = "INSERT INTO sucursal_producto (sucursal_id, producto_id, estado) VALUES (?,?,?)";
    private static final String ACTUALIZAR_ESTADO = "UPDATE sucursal_producto SET estado = NOT estado WHERE sucursal_id = ? AND producto_id = ?";
    private static final String ELIMINAR = "DELETE FROM sucursal_producto WHERE sucursal_id = ? AND producto_id = ?";
    private static final String EXISTE_PRODUCTO_SUCURSAL = "SELECT sucursal_id FROM sucursal_producto WHERE sucursal_id = ? AND producto_id = ?";
    private static final String OBTENER_POR_SUCURSAL = "SELECT sp.*, p.nombre AS nombre_producto FROM sucursal_producto AS sp JOIN producto AS p ON sp.producto_id = p.producto_id WHERE sp.sucursal_id = ?";


    public boolean existeProductoSucursal(int sucursalId, int productoId) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement unique = connection.prepareStatement(EXISTE_PRODUCTO_SUCURSAL)) {
            unique.setInt(1, sucursalId);
            unique.setInt(2, productoId);
            try (ResultSet resultSet = unique.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public void insertar(SucursalProducto entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            insert.setInt(1, entity.getSucursalId());
            insert.setInt(2, entity.getProductoId());
            insert.setBoolean(3, entity.isEstado());
            insert.executeUpdate();
        }
    }

    @Override
    public Optional<SucursalProducto> obtenerPorId(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<SucursalProducto> obtenerTodo() throws SQLException {
        return List.of();
    }

    public List<SucursalProducto> obtenerProductoSucursal(int sucursalId) throws SQLException {
        List<SucursalProducto> list = new ArrayList<>();
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_POR_SUCURSAL)) {
            select.setInt(1, sucursalId);
            try (ResultSet resultSet = select.executeQuery()) {
                while (resultSet.next()) {
                    SucursalProducto sucursalProducto = new SucursalProducto(resultSet.getInt("sucursal_id"),
                            resultSet.getInt("producto_id"),
                            resultSet.getBoolean("estado"));
                    sucursalProducto.setNombreProducto(resultSet.getString("nombre_producto"));
                    list.add(sucursalProducto);
                }
                return list;
            }
        }
    }

    @Override
    public void actualizar(SucursalProducto entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_ESTADO)) {
            update.setInt(1, entity.getSucursalId());
            update.setInt(2, entity.getProductoId());
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
