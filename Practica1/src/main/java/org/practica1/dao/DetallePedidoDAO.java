package org.practica1.dao;

import org.practica1.config.Conexion;
import org.practica1.models.DetallePedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DetallePedidoDAO implements CRUD<DetallePedido> {

    private static final String INSERTAR = "INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad) VALUES (?,?,?)";
    private static final String OBTENER_TODO_DETALLE_PEDIDO = "SELECT * FROM detalle_pedido WHERE pedido_id = ?";

    @Override
    public void insertar(DetallePedido entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            insert.setInt(1, entity.getPedidoId());
            insert.setInt(2, entity.getProductoId());
            insert.setInt(3, entity.getCantidad());
            insert.executeUpdate();
        }
    }

    public void insertar(DetallePedido entity, Connection connection) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR)) {
            insert.setInt(1, entity.getPedidoId());
            insert.setInt(2, entity.getProductoId());
            insert.setInt(3, entity.getCantidad());
            insert.executeUpdate();
        }
    }

    @Override
    public Optional<DetallePedido> obtenerPorId(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<DetallePedido> obtenerTodo() throws SQLException {
        return List.of();
    }

    public List<DetallePedido> obtenerTodo(int pedidoId) throws SQLException {
        List<DetallePedido> list = new ArrayList<>();
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_TODO_DETALLE_PEDIDO)){
            select.setInt(1,pedidoId);
            try (ResultSet resultSet = select.executeQuery()){
                while (resultSet.next()) {
                    DetallePedido detallePedido = new DetallePedido(resultSet.getInt("pedido_id"),
                            resultSet.getInt("producto_id"),
                            resultSet.getInt("cantidad"));
                    list.add(detallePedido);
                }
                return list;
            }
        }
    }

    @Override
    public void actualizar(DetallePedido entity) throws SQLException {

    }

    @Override
    public void eliminar(int id) throws SQLException {

    }
}
