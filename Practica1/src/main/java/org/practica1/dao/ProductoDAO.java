package org.practica1.dao;

import org.practica1.config.Conexion;
import org.practica1.models.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDAO implements CRUD<Producto> {

    private static final String INSERTAR = "INSERT INTO producto (nombre, tiempo_base_preparacion) VALUE (?,?)";
    private static final String ACTUALIZAR = "UPDATE producto SET nombre = ?, tiempo_base_preparacion = ? WHERE producto_id = ?";
    private static final String OBTENER_TODO = "SELECT * FROM producto";
    private static final String OBTENER_POR_ID = "SELECT * FROM producto WHERE producto_id = ?";
    private static final String ELIMINAR = "DELETE FROM producto WHERE producto_id = ?"; // se podrá eliminar si no está relacionado a una sucursal
    private static final String EXISTE_NOMBRE = "SELECT producto_id FROM producto WHERE nombre = ?";
    private static final String VALIDAR_NOMBRE_ACTUALIZAR = "SELECT producto_id FROM producto WHERE nombre = ? AND producto_id <> ?";

    public boolean existeNombre(String nombre) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement validar = connection.prepareStatement(EXISTE_NOMBRE)) {
            validar.setString(1,nombre);
            try (ResultSet resultSet = validar.executeQuery()){
                return resultSet.next();
            }
        }
    }

    public boolean validarNombreActualizar(String nombre, int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement validar = connection.prepareStatement(VALIDAR_NOMBRE_ACTUALIZAR)) {
            validar.setString(1,nombre);
            validar.setInt(2,id);
            try (ResultSet resultSet = validar.executeQuery()){
                return resultSet.next();
            }
        }
    }

    @Override
    public void insertar(Producto entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            insert.setString(1, entity.getNombre());
            insert.setInt(2, entity.getTiempoBaseDePreparacion());
            insert.executeUpdate();
        }
    }

    @Override
    public Optional<Producto> obtenerPorId(int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_POR_ID)) {
            select.setInt(1, id);
            try (ResultSet resultSet = select.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(extraerDatos(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Producto> obtenerTodo() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_TODO);
             ResultSet resultSet = select.executeQuery()) {
            while (resultSet.next()) {
                productos.add(extraerDatos(resultSet));
            }
            return productos;
        }
    }

    @Override
    public void actualizar(Producto entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR)) {
            update.setString(1, entity.getNombre());
            update.setInt(2, entity.getTiempoBaseDePreparacion());
            update.setInt(3, entity.getProductoID());
            update.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR)) {
            delete.setInt(1, id);
            delete.executeUpdate();
        }
    }

    private Producto extraerDatos(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto(resultSet.getInt("tiempo_base_preparacion"),
                resultSet.getString("nombre"));
        producto.setProductoID(resultSet.getInt("producto_id"));
        return producto;
    }
}
