package org.practica1.controllers;

import org.practica1.dao.ReportesDAO;
import org.practica1.models.ReporteEstadistica;
import org.practica1.models.ReporteRanking;
import org.practica1.utils.ExportadorCSV;
import org.practica1.views.AdminTiendaFrame;
import org.practica1.views.PanelReporteBase;
import org.practica1.views.SuperAdminFrame;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportesController {

    private final ReportesDAO reportesDAO;

    public ReportesController(SuperAdminFrame vista, ReportesDAO reportesDAO) {
        this.reportesDAO = reportesDAO;
        configurarPanelRanking(vista.getPanelRanking(), null, vista);
        configurarPanelEstadisticas(vista.getPanelEstadisticas(), null, vista);
    }

    // CONSTRUCTOR 2: Para el Administrador de Tienda (Filtra por la sucursal del Admin logueado)
    // Asume que tu frame se llama AdminTiendaFrame
    public ReportesController(AdminTiendaFrame vista, ReportesDAO reportesDAO, int sucursalId) {
        this.reportesDAO = reportesDAO;
        configurarPanelRanking(vista.getPanelRanking(), sucursalId, vista);
        configurarPanelEstadisticas(vista.getPanelEstadisticas(), sucursalId, vista);
    }

    private void configurarPanelRanking(PanelReporteBase panel, Integer sucursalId, JFrame ventanaPadre) {
        cargarDatosRanking(panel, sucursalId, ventanaPadre);

        panel.getBtnActualizar().addActionListener(e -> cargarDatosRanking(panel, sucursalId, ventanaPadre));

        panel.getBtnExportar().addActionListener(e -> {
            String nombreArchivo = (sucursalId == null) ? "Ranking_Global" : "Ranking_MiSucursal";
            ExportadorCSV.exportarTabla(panel.getTablaReporte(), ventanaPadre, nombreArchivo);
        });
    }

    private void cargarDatosRanking(PanelReporteBase panel, Integer sucursalId, JFrame ventanaPadre) {
        try {
            panel.getModeloTabla().setRowCount(0); // Limpiar tabla

            // Decidimos qué método del DAO usar según el tipo de Admin
            List<ReporteRanking> datos;
            if (sucursalId == null) {
                datos = reportesDAO.obtenerRankingGlobal();
            } else {
                datos = reportesDAO.obtenerRankingSucursal(sucursalId);
            }

            // Llenamos las filas
            for (int i = 0; i < datos.size(); i++) {
                ReporteRanking r = datos.get(i);
                panel.getModeloTabla().addRow(new Object[]{
                        (i + 1) + ". " + r.getNombreJugador(),
                        r.getNombreSucursal(),
                        "Nivel " + r.getNivelMaximo(),
                        r.getPuntajeTotal() + " pts"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ventanaPadre, "Error al cargar Ranking: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarPanelEstadisticas(PanelReporteBase panel, Integer sucursalId, JFrame ventanaPadre) {

        cargarDatosEstadisticas(panel, sucursalId, ventanaPadre);

        panel.getBtnActualizar().addActionListener(e -> {
            cargarDatosEstadisticas(panel, sucursalId, ventanaPadre);
        });

        panel.getBtnExportar().addActionListener(e -> {
            String nombreArchivo = (sucursalId == null) ? "Estadisticas_Globales" : "Estadisticas_MiSucursal";
            ExportadorCSV.exportarTabla(panel.getTablaReporte(), ventanaPadre, nombreArchivo);
        });
    }

    private void cargarDatosEstadisticas(PanelReporteBase panel, Integer sucursalId, JFrame ventanaPadre) {
        try {
            panel.getModeloTabla().setRowCount(0); // Limpiar tabla
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // Decidimos qué método del DAO usar
            List<ReporteEstadistica> datos;
            if (sucursalId == null) {
                datos = reportesDAO.obtenerEstadisticaGlobal();
            } else {
                datos = reportesDAO.obtenerEstadisticasPorSucursal(sucursalId);
            }

            // Llenamos las filas
            for (ReporteEstadistica e : datos) {
                panel.getModeloTabla().addRow(new Object[]{
                        "#" + e.getPartidaId(),
                        e.getNombreJugador(),
                        e.getNombreSucursal(),
                        e.getFechaInicio().format(format),
                        e.getFechaFin() != null ? e.getFechaFin().format(format) : "---",
                        e.getNivel(),
                        e.getPuntaje()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ventanaPadre, "Error al cargar Estadísticas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}
