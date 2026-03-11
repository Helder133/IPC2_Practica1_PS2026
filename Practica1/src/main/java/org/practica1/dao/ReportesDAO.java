package org.practica1.dao;

import org.practica1.config.Conexion;
import org.practica1.models.ReporteEstadistica;
import org.practica1.models.ReporteRanking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportesDAO {

    private static final String RANKING_GLOBAL = "SELECT u.nombre AS jugador, s.nombre AS sucursal, MAX(p.nivel) AS nivel_max, SUM(p.puntaje) AS puntos_totales FROM partida p JOIN usuario u ON p.usuario_id = u.usuario_id JOIN sucursal s ON u.sucursal_id = s.sucursal_id GROUP BY u.usuario_id, u.nombre, s.nombre ORDER BY puntos_totales DESC;";
    private static final String RANKING_SUCURSAL = "SELECT u.nombre AS jugador, s.nombre AS sucursal, MAX(p.nivel) AS nivel_max, SUM(p.puntaje) AS puntos_totales FROM partida p JOIN usuario u ON p.usuario_id = u.usuario_id JOIN sucursal s ON u.sucursal_id = s.sucursal_id WHERE u.sucursal_id = ? GROUP BY u.usuario_id, u.nombre, s.nombre ORDER BY puntos_totales DESC";
    private static final String ESTADISTICAS_GLOBAL = "SELECT p.partida_id, u.nombre AS jugador, s.nombre AS sucursal, p.fecha_inicio, p.fecha_fin , p.nivel, p.puntaje FROM partida p JOIN usuario u ON p.usuario_id = u.usuario_id JOIN sucursal s ON u.sucursal_id = s.sucursal_id ORDER BY p.fecha_inicio DESC";
    private static final String ESTADISTICAS_SUCURSAL = "SELECT p.partida_id, u.nombre AS jugador, s.nombre AS sucursal, p.fecha_inicio, p.fecha_fin , p.nivel, p.puntaje FROM partida p JOIN usuario u ON p.usuario_id = u.usuario_id JOIN sucursal s ON u.sucursal_id = s.sucursal_id WHERE u.sucursal_id = ? ORDER BY p.fecha_inicio DESC";

    public List<ReporteRanking> obtenerRankingGlobal() throws SQLException {
        List<ReporteRanking> lista = new ArrayList<>();
        Connection conn = Conexion.getInstancia().getConnection();
        try (PreparedStatement rankingGlobal = conn.prepareStatement(RANKING_GLOBAL);
             ResultSet rs = rankingGlobal.executeQuery()) {
            while (rs.next()) {
                lista.add(new ReporteRanking(
                        rs.getString("jugador"), rs.getString("sucursal"),
                        rs.getInt("nivel_max"), rs.getInt("puntos_totales")
                ));
            }
        }
        return lista;
    }

    public List<ReporteRanking> obtenerRankingSucursal(int sucursalId) throws SQLException {
        List<ReporteRanking> lista = new ArrayList<>();
        Connection conn = Conexion.getInstancia().getConnection();
        try (PreparedStatement rankingSucursal = conn.prepareStatement(RANKING_SUCURSAL)) {
            rankingSucursal.setInt(1, sucursalId);
            try (ResultSet rs = rankingSucursal.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ReporteRanking(
                            rs.getString("jugador"), rs.getString("sucursal"),
                            rs.getInt("nivel_max"), rs.getInt("puntos_totales")
                    ));
                }
            }
        }
        return lista;
    }

    public List<ReporteEstadistica> obtenerEstadisticaGlobal() throws SQLException {
        List<ReporteEstadistica> lista = new ArrayList<>();
        Connection conn = Conexion.getInstancia().getConnection();
        try (PreparedStatement estadistica = conn.prepareStatement(ESTADISTICAS_GLOBAL);
             ResultSet rs = estadistica.executeQuery()) {
            while (rs.next()) {
                lista.add(new ReporteEstadistica(
                        rs.getInt("partida_id"), rs.getString("jugador"), rs.getString("sucursal"),
                        rs.getTimestamp("fecha_inicio").toLocalDateTime(),
                        rs.getTimestamp("fecha_fin") != null ? rs.getTimestamp("fecha_fin").toLocalDateTime(): null,
                        rs.getInt("nivel"), rs.getInt("puntaje")
                ));
            }
        }
        return lista;
    }

    public List<ReporteEstadistica> obtenerEstadisticasPorSucursal(int sucursalId) throws SQLException {
        List<ReporteEstadistica> lista = new ArrayList<>();
        Connection conn = Conexion.getInstancia().getConnection();
        try (PreparedStatement estadistica = conn.prepareStatement(ESTADISTICAS_SUCURSAL)) {
            estadistica.setInt(1, sucursalId);
            try (ResultSet rs = estadistica.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ReporteEstadistica(
                            rs.getInt("partida_id"), rs.getString("jugador"), rs.getString("sucursal"),
                            rs.getTimestamp("fecha_inicio").toLocalDateTime(),
                            rs.getTimestamp("fecha_fin")!= null ? rs.getTimestamp("fecha_fin").toLocalDateTime(): null,
                            rs.getInt("nivel"), rs.getInt("puntaje")
                    ));
                }
            }
        }
        return lista;
    }

}
