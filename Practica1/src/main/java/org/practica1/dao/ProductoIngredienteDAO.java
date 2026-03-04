package org.practica1.dao;

import org.practica1.config.Conexion;
import org.practica1.models.ProductoIngrediente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoIngredienteDAO implements CRUD<ProductoIngrediente> {
    private static final String INSERTAR = "INSERT INTO producto_ingrediente (producto_id,ingrediente_id) VALUES (?,?)";
    private static final String OBTENER_INGREDIENTES_DE_PRODUCTO = "SELECT * FROM producto_ingrediente WHERE producto_id = ?";
    private static final String VALIDAR_UNICO_INGREDIENTE = "SELECT producto_id FROM producto_ingrediente WHERE producto_id = ? AND ingrediente_id = ?";
    private static final String ELIMINAR = "DELETE FROM producto_ingrediente WHERE producto_id = ? AND ingrediente_id = ?";

    public boolean existeIngredienteEnProducto(int producto_id, int ingrediente_id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement validar = connection.prepareStatement(VALIDAR_UNICO_INGREDIENTE)) {
            validar.setInt(1,producto_id);
            validar.setInt(2,ingrediente_id);
            try (ResultSet resultSet = validar.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public void insertar(ProductoIngrediente entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            insert.setInt(1, entity.getProductoId());
            insert.setInt(2, entity.getIngredienteId());
            insert.executeUpdate();
        }
    }

    public List<ProductoIngrediente> obtenerTodosLosIngredientes(int producto_id) throws SQLException {
        List<ProductoIngrediente> productoIngredientes = new ArrayList<>();
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_INGREDIENTES_DE_PRODUCTO)) {
            select.setInt(1, producto_id);
            try (ResultSet resultSet = select.executeQuery()) {
                while (resultSet.next()) {
                    productoIngredientes.add(new ProductoIngrediente(resultSet.getInt("producto_id"),
                            resultSet.getInt("ingrediente_id")));
                }
                return productoIngredientes;
            }
        }
    }

    public void eliminar(int producto_id, int ingrediente_id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR)) {
            delete.setInt(1,producto_id);
            delete.setInt(2,ingrediente_id);
            delete.executeUpdate();
        }
    }

    @Override
    public Optional<ProductoIngrediente> obtenerPorId(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<ProductoIngrediente> obtenerTodo() throws SQLException {
        return List.of();
    }

    @Override
    public void actualizar(ProductoIngrediente entity) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }
}
