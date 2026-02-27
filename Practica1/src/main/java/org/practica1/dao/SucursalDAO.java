package org.practica1.dao;

import org.practica1.config.Conexion;
import org.practica1.models.Sucursal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class SucursalDAO implements CRUD<Sucursal> {

    private static final String INSERTAR = "INSERT INTO sucursal (nombre, contacto, ubicacion) VALUES (?,?,?)";
    private static final String ACTUALIZAR = "UPDATE sucursal SET nombre = ?, contacto = ?, ubicacion = ? WHERE sucursal_id = ?";
    private static final String OBTENER_TODO = "SELECT * FROM sucursal";
    private static final String OBTENER_POR_ID = "SELECT * FROM sucursal WHERE sucursal_id = ?";
    private static final String ELIMINAR = "DELETE FROM sucursal WHERE sucursal_id = ?";
    private static final String VALIDAR_UBICACION = "SELECT sucursal_id FROM sucursal WHERE ubicacion = ?";

    public boolean existeUbicacion(String ubicacion) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement validar = connection.prepareStatement(VALIDAR_UBICACION)) {
            validar.setString(1,ubicacion);
            try (ResultSet resultSet = validar.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public void insertar(Sucursal entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            insert.setString(1, entity.getNombre());
            insert.setString(2, entity.getContacto());
            insert.setString(3, entity.getUbicacion());
            insert.executeUpdate();
        }
    }

    @Override
    public Optional<Sucursal> obtenerPorId(int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_POR_ID)) {
            select.setInt(1, id);
            try (ResultSet resultSet = select.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(extraerSucursal(resultSet));
                }
            }
            return Optional.empty();
        }

    }

    @Override
    public List<Sucursal> obtenerTodo() throws SQLException {
        List<Sucursal> sucursals = new ArrayList<>();
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_TODO);
             ResultSet resultSet = select.executeQuery()) {
            while (resultSet.next()) {
                sucursals.add(extraerSucursal(resultSet));
            }
            return sucursals;
        }
    }

    @Override
    public void actualizar(Sucursal entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR)) {
            update.setString(1,entity.getNombre());
            update.setString(2,entity.getContacto());
            update.setString(3,entity.getUbicacion());
            update.setInt(4,entity.getSucursal_id());
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

    private Sucursal extraerSucursal(ResultSet resultSet) throws SQLException{
        Sucursal sucursal = new Sucursal(
                resultSet.getString("nombre"),
                resultSet.getString("contacto"),
                resultSet.getString("ubicacion"));
        sucursal.setSucursal_id(resultSet.getInt("sucursal_id"));
        return sucursal;
    }

}
