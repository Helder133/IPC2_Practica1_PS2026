package org.practica1.controllers;

import org.practica1.dao.PartidaDAO;
import org.practica1.models.ResumenPartida;
import org.practica1.views.HistorialDialog;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistorialController {

    private HistorialDialog vista;
    private PartidaDAO partidaDAO;
    private int usuarioId;

    public HistorialController(HistorialDialog vista, PartidaDAO partidaDAO, int usuarioId) {
        this.vista = vista;
        this.partidaDAO = partidaDAO;
        this.usuarioId = usuarioId;

        // Evento para cerrar la ventana
        this.vista.getBtnCerrar().addActionListener(e -> vista.dispose());

        // Llenar la tabla al instante
        cargarDatosEnTabla();
    }

    private void cargarDatosEnTabla() {
        try {
            List<ResumenPartida> historial = partidaDAO.obtenerHistorialJugador(usuarioId);
            vista.getModeloTabla().setRowCount(0); // Limpiamos la tabla

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (ResumenPartida rp : historial) {
                vista.getModeloTabla().addRow(new Object[]{
                        "#" + rp.getPartidaId(),
                        rp.getFechaInicio().format(formatter),
                        rp.getNivel(),
                        rp.getPuntaje() + " pts",
                        rp.getPedidosAtendidos()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar el historial: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
