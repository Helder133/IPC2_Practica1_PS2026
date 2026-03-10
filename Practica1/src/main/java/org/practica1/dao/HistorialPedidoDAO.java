package org.practica1.dao;

import org.practica1.config.Conexion;
import org.practica1.models.EnumPedido;
import org.practica1.models.HistorialPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistorialPedidoDAO implements CRUD<HistorialPedido> {

    private static final String INSERTAR = "INSERT INTO historial_pedido (pedido_id, fecha, estado) VALUES (?,?,?) ";
    private static final String OBTENER_HISTORIAL_PEDIDO = "SELECT * FROM historial_pedido WHERE pedido_id = ?";

    @Override
    public void insertar(HistorialPedido entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            insert.setInt(1,entity.getPedidoId());
            insert.setTimestamp(2, Timestamp.valueOf(entity.getFecha()));
            insert.setString(3,entity.getEstado().toString());
            insert.executeUpdate();
        }
    }

    public void insertar(HistorialPedido entity, Connection connection) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            insert.setInt(1,entity.getPedidoId());
            insert.setTimestamp(2, Timestamp.valueOf(entity.getFecha()));
            insert.setString(3,entity.getEstado().toString());
            insert.executeUpdate();
        }
    }

    @Override
    public Optional<HistorialPedido> obtenerPorId(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<HistorialPedido> obtenerTodo() throws SQLException {
        return List.of();
    }

    public List<HistorialPedido> obtenerTodo(int pedidoId) throws SQLException {
        List<HistorialPedido> list = new ArrayList<>();
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_HISTORIAL_PEDIDO)) {
            select.setInt(1,pedidoId);
            try (ResultSet resultSet = select.executeQuery()){
                while (resultSet.next()) {
                    HistorialPedido historialPedido = new HistorialPedido(resultSet.getInt("pedido_id"));
                    historialPedido.setHistorialId(resultSet.getInt("historial_id"));
                    historialPedido.setEstado(EnumPedido.valueOf(resultSet.getString("estado")));
                    historialPedido.setFecha(resultSet.getTimestamp("fecha").toLocalDateTime());
                    list.add(historialPedido);
                }
                return list;
            }
        }
    }

    @Override
    public void actualizar(HistorialPedido entity) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }
}
