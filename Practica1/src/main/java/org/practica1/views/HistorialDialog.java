package org.practica1.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HistorialDialog extends JDialog {

    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JButton btnCerrar;

    public HistorialDialog(JFrame parent) {
        super(parent, "Historial de Partidas", true); // true = Modal (Bloquea la ventana de atrás)
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Configuramos las columnas de la tabla
        String[] columnas = {"ID Partida", "Fecha de Juego", "Nivel Alcanzado", "Puntaje Final", "Pedidos Atendidos"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            } // Tabla de solo lectura
        };

        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setRowHeight(25);
        tablaHistorial.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(scrollPane, BorderLayout.CENTER);

        // Botón inferior
        JPanel panelInferior = new JPanel();
        btnCerrar = new JButton("Cerrar Historial");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        panelInferior.add(btnCerrar);
        this.add(panelInferior, BorderLayout.SOUTH);
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JButton getBtnCerrar() {
        return btnCerrar;
    }

}
