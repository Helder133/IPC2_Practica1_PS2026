package org.practica1.dao;

import org.practica1.config.Conexion;
import org.practica1.models.Partida;
import org.practica1.models.ResumenPartida;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PartidaDAO implements CRUD<Partida> {

    private static final String INSERTAR = "INSERT INTO partida (usuario_id, sucursal_id, fecha_inicio) VALUES (?, ?, ?)";
    private static final String ACTUALIZAR_PROGRESO = "UPDATE partida SET nivel = ?, puntaje = ?, fecha_fin = ? WHERE partida_id = ?";
    private static final String OBTENER_POR_ID = "SELECT * FROM partida WHERE partida_id = ?";
    private static final String VERIFICAR_SI_EXISTE_PARTIDA = "SELECT * FROM partida WHERE usuario_id = ? AND fecha_fin IS NULL";

    public int iniciarPartida(Partida entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement insert = connection.prepareStatement(INSERTAR, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insert.setInt(1, entity.getUsuarioId());
            insert.setInt(2, entity.getSucursalId());
            insert.setTimestamp(3, Timestamp.valueOf(entity.getFechaInicio()));

            int filasAfectadas = insert.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet resultSet = insert.getGeneratedKeys()) {
                    if (resultSet.next()) return resultSet.getInt(1);
                }
            }
            throw new SQLException("Fallo al iniciar la partida, no se obtuvo el ID.");
        }
    }

    @Override
    public void insertar(Partida entity) throws SQLException {

    }

    @Override
    public Optional<Partida> obtenerPorId(int id) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(OBTENER_POR_ID)) {
            select.setInt(1, id);
            try (ResultSet resultSet = select.executeQuery()) {
                if (resultSet.next()) {

                    return Optional.of(extraerDatos(resultSet));
                }
            }
            return Optional.empty();
        }
    }

    public Optional<Partida> verificarSiExistePartida(int usuarioId) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement select = connection.prepareStatement(VERIFICAR_SI_EXISTE_PARTIDA)) {
            select.setInt(1, usuarioId);
            try (ResultSet resultSet = select.executeQuery()) {
                if (resultSet.next()) {

                    return Optional.of(extraerDatos(resultSet));
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Partida> obtenerTodo() throws SQLException {
        return List.of();
    }

    @Override
    public void actualizar(Partida entity) throws SQLException {
        Connection connection = Conexion.getInstancia().getConnection();
        try (PreparedStatement update = connection.prepareStatement(ACTUALIZAR_PROGRESO)) {
            update.setInt(1, entity.getNivel());
            update.setInt(2, entity.getPuntaje());
            if (entity.getFechaFin() == null) {
                update.setNull(3, Types.TIMESTAMP);
            } else {
                update.setTimestamp(3, Timestamp.valueOf(entity.getFechaFin()));
            }
            update.setInt(4, entity.getPartidaId());

            update.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {

    }

    private Partida extraerDatos(ResultSet resultSet) throws SQLException {
        Partida partida = new Partida(resultSet.getInt("usuario_id"),
                resultSet.getInt("sucursal_id"));
        partida.setPartidaId(resultSet.getInt("partida_id"));
        partida.setNivel(resultSet.getInt("nivel"));
        partida.setPuntaje(resultSet.getInt("puntaje"));
        partida.setFechaInicio(resultSet.getTimestamp("fecha_inicio").toLocalDateTime());
        if (resultSet.getTimestamp("fecha_fin") != null)
            partida.setFechaFin(resultSet.getTimestamp("fecha_fin").toLocalDateTime());
        return partida;
    }

    public List<ResumenPartida> obtenerHistorialJugador(int usuarioId) throws SQLException {
        List<ResumenPartida> lista = new java.util.ArrayList<>();
        Connection connection = Conexion.getInstancia().getConnection();

        // SQL: Traemos la partida y contamos cuántos pedidos están enlazados a ella
        String sql = "SELECT pa.partida_id, pa.fecha_inicio, pa.fecha_fin, pa.nivel, pa.puntaje, COUNT(pe.pedido_id) AS total_pedidos FROM partida pa LEFT JOIN pedido pe ON pa.partida_id = pe.partida_id WHERE pa.usuario_id = ? GROUP BY pa.partida_id, pa.fecha_inicio, pa.nivel, pa.puntaje ORDER BY pa.fecha_inicio DESC;\n"; // Las más recientes primero

        try (PreparedStatement select = connection.prepareStatement(sql)) {
            select.setInt(1, usuarioId);
            try (java.sql.ResultSet rs = select.executeQuery()) {
                while (rs.next()) {
                    int partidaId = rs.getInt("partida_id");
                    LocalDateTime fecha = rs.getTimestamp("fecha_inicio").toLocalDateTime();
                    LocalDateTime fechaFin = rs.getTimestamp("fecha_fin") != null ? rs.getTimestamp("fecha_fin").toLocalDateTime() : null;
                    int nivel = rs.getInt("nivel");
                    int puntaje = rs.getInt("puntaje");
                    int totalPedidos = rs.getInt("total_pedidos");

                    lista.add(new ResumenPartida(partidaId, fecha, fechaFin,nivel, puntaje, totalPedidos));
                }
            }
        }
        return lista;
    }
}
