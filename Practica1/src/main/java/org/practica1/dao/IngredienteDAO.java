package org.practica1.dao;

import org.practica1.config.Conexion;
import org.practica1.models.Ingrediente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IngredienteDAO implements CRUD<Ingrediente> {

    private static final String INSERTAR = "INSERT INTO  ingrediente (nombre) VALUES (?)";
    private static final String ACTUALIZAR = "UPDATE ingrediente SET nombre = ? WHERE ingrediente_id = ?";
    private static final String OBTENER_TODO = "SELECT * FROM ingrediente";
    private static final String OBTENER_POR_ID = "SELECT * FROM ingrediente WHERE ingrediente_id = ?";
    private static final String ELIMINAR = "DELETE FROM ingrediente WHERE ingrediente_id = ?";
    private static final String EXISTE_NOMBRE = "SELECT ingrediente_id FROM ingrediente WHERE nombre = ?";
    private static final String VALIDAR_NOMBRE_ACTUALIZAR = "SELECT ingrediente_id FROM ingrediente WHERE nombre = ? AND ingrediente_id <> ?";

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
    public void insertar(Ingrediente entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            insert.setString(1, entity.getNombre());
            insert.executeUpdate();
        }
    }

    @Override
    public Optional<Ingrediente> obtenerPorId(int id) throws SQLException {
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
    public List<Ingrediente> obtenerTodo() throws SQLException {
        List<Ingrediente> ingredientes = new ArrayList<>();
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_TODO);
             ResultSet resultSet = select.executeQuery()) {
            while (resultSet.next()) {
                ingredientes.add(extraerDatos(resultSet));
            }
            return ingredientes;
        }
    }

    @Override
    public void actualizar(Ingrediente entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR)) {
            update.setString(1,entity.getNombre());
            update.setInt(2,entity.getIngredienteId());
            update.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement delete = connection.prepareStatement(ELIMINAR)) {
            delete.setInt(1,id);
            delete.executeUpdate();
        }
    }

    private Ingrediente extraerDatos(ResultSet resultSet) throws SQLException {
        Ingrediente ingrediente = new Ingrediente(resultSet.getString("nombre"));
        ingrediente.setIngredienteId(resultSet.getInt("ingrediente_id"));
        return ingrediente;
    }

}
