package org.practica1.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelReporteBase extends JPanel {

    private JTable tablaReporte;
    private DefaultTableModel modeloTabla;
    private JButton btnExportar;
    private JButton btnActualizar; // Nuevo botón para refrescar datos

    public PanelReporteBase(String tituloReporte, String[] columnas) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(236, 240, 241)); // Fondo gris claro
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Margen interno

        inicializarComponentes(tituloReporte, columnas);
    }

    private void inicializarComponentes(String titulo, String[] columnas) {
        // Título superior
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(44, 62, 80));
        this.add(lblTitulo, BorderLayout.NORTH);

        // Tabla de datos
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaReporte = new JTable(modeloTabla);
        tablaReporte.setRowHeight(25);
        tablaReporte.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaReporte);
        this.add(scrollPane, BorderLayout.CENTER);

        // Botones inferiores
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);

        btnActualizar = new JButton("↻ Actualizar Datos");

        btnExportar = new JButton("📥 Exportar a CSV");
        btnExportar.setBackground(new Color(46, 204, 113));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFont(new Font("Arial", Font.BOLD, 12));

        panelBotones.add(btnActualizar);
        panelBotones.add(btnExportar);
        this.add(panelBotones, BorderLayout.SOUTH);
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JTable getTablaReporte() {
        return tablaReporte;
    }

    public JButton getBtnExportar() {
        return btnExportar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

}
