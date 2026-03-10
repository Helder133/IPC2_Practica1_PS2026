package org.practica1.dao;

import com.mysql.cj.xdevapi.Type;
import org.practica1.config.Conexion;
import org.practica1.models.EnumPedido;
import org.practica1.models.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoDAO implements CRUD<Pedido> {

    private static final String INSERTAR = "INSERT INTO pedido (partida_id, tiempo_limite, fecha_creacion) VALUES (?,?,?)";
    private static final String ACTUALIZAR = "UPDATE pedido SET fecha_entrega = ?, estado = ?, puntaje_obtenido = ? WHERE pedido_id = ?";
    private static final String OBTENER_TODO = "SELECT * FROM pedido";
    private static final String OBTENER_POR_ID = "SELECT * FROM pedido WHERE pedido_id = ?";

    @Override
    public void insertar(Pedido entity) throws SQLException {
    }

    public int insertar(Pedido entity, Connection connection) throws SQLException {
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insert.setInt(1, entity.getPartidaId());
            insert.setInt(2, entity.getTiempoLimite());
            insert.setTimestamp(3, Timestamp.valueOf(entity.getFechaCreacion()));
            int filasAfectadas = insert.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet resultSet = insert.getGeneratedKeys()) {
                    if (resultSet.next()) return resultSet.getInt(1);
                }
            }
            throw new SQLException("Fallo al crear el pedido, no se obtuvo el ID.");
        }
    }

    @Override
    public Optional<Pedido> obtenerPorId(int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_POR_ID)) {
            select.setInt(1, id);
            try (ResultSet resultSet = select.executeQuery()) {
                if (resultSet.next()) return Optional.of(extraerDatos(resultSet));
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Pedido> obtenerTodo() throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        List<Pedido> list = new ArrayList<>();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_TODO);
             ResultSet resultSet = select.executeQuery()) {
            while (resultSet.next()) list.add(extraerDatos(resultSet));
            return list;
        }
    }

    @Override
    public void actualizar(Pedido entity) throws SQLException {

    }

    public void actualizar(Pedido entity, Connection connection) throws SQLException {
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR)) {
            if (entity.getFechaEntrega() == null) {
                update.setNull(1, Types.TIMESTAMP);
            } else {
                update.setTimestamp(1, Timestamp.valueOf(entity.getFechaEntrega()));
            }
            update.setString(2,entity.getEstado().toString());
            update.setInt(3,entity.getPuntajeObtenido());
            update.setInt(4,entity.getPedidoId());
            update.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    private Pedido extraerDatos(ResultSet resultSet) throws SQLException {
        Pedido pedido = new Pedido(resultSet.getInt("partida_id"));
        pedido.setPedidoId(resultSet.getInt("pedido_id"));
        pedido.setTiempoLimite(resultSet.getInt("tiempo_limite"));
        pedido.setFechaCreacion(resultSet.getTimestamp("fecha_creacion").toLocalDateTime());
        if (resultSet.getTimestamp("fecha_entrega") != null)
            pedido.setFechaEntrega(resultSet.getTimestamp("fecha_entrega").toLocalDateTime());
        pedido.setEstado(EnumPedido.valueOf(resultSet.getString("estado")));
        pedido.setPuntajeObtenido(resultSet.getInt("puntaje_obtenido"));
        return pedido;
    }

}
