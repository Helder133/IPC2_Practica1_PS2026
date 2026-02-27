package org.practica1.views;

import org.practica1.models.Sucursal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SuperAdminFrame extends JFrame {

    private JTextField txtNombreSucursal;
    private JTextField txtContactoSucursal;
    private JTextField txtUbicacionSucursal;
    private JButton btnGuardarSucursal;
    private JButton btnEliminarSucursal;
    private JButton btnLimpiarSucursal;

    private JTable tablaSucursales;
    private DefaultTableModel modeloSucursales;

    private Font titulo = new Font("Arial", Font.BOLD, 18);
    private Font principal = new Font("Helvetica", Font.BOLD, 14);
    private Font secundario = new Font("Helvetica", Font.PLAIN, 13);

    public SuperAdminFrame() {
        setTitle("Pizza Express Tycoon - Panel de Super Administrador");
        setSize(850, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(principal);

        JPanel panelSucursal = crearPanelSucursal();
        tabbedPane.addTab("Gestion de sucursales", null, panelSucursal, "Administrar las tiendas");

        JPanel panelUsuarios = new JPanel();
        panelUsuarios.setBackground(new Color(30, 30, 30));
        tabbedPane.addTab("Gestión de Usuarios", null, panelUsuarios, "Administrar empleados y jugadores");

        add(tabbedPane);
    }

    private JPanel crearPanelSucursal() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(30, 30, 30));

        Color textColor = Color.LIGHT_GRAY;

        JLabel lblTitulo = new JLabel("Datos de la sucursal");
        lblTitulo.setFont(titulo);
        lblTitulo.setForeground(new Color(241, 196, 15));
        lblTitulo.setBounds(20, 20, 300, 30);
        panel.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(principal);
        lblNombre.setForeground(textColor);
        lblNombre.setBounds(20, 70, 250, 20);
        panel.add(lblNombre);

        txtNombreSucursal = new JTextField();
        txtNombreSucursal.setFont(secundario);
        txtNombreSucursal.setBounds(20, 90, 250, 30);
        panel.add(txtNombreSucursal);

        JLabel lblContacto = new JLabel("Contacto (Teléfono):");
        lblContacto.setFont(principal);
        lblContacto.setForeground(textColor);
        lblContacto.setBounds(20, 130, 250, 20);
        panel.add(lblContacto);

        txtContactoSucursal = new JTextField();
        txtContactoSucursal.setFont(secundario);
        txtContactoSucursal.setBounds(20, 150, 250, 30);
        panel.add(txtContactoSucursal);

        JLabel lblUbicacion = new JLabel("Ubicación:");
        lblUbicacion.setFont(principal);
        lblUbicacion.setForeground(textColor);
        lblUbicacion.setBounds(20, 190, 250, 20);
        panel.add(lblUbicacion);

        txtUbicacionSucursal = new JTextField();
        txtUbicacionSucursal.setFont(secundario);
        txtUbicacionSucursal.setBounds(20, 210, 250, 30);
        panel.add(txtUbicacionSucursal);

        btnGuardarSucursal = new JButton("Guardar / Actualizar");
        btnGuardarSucursal.setBounds(20, 270, 250, 35);
        btnGuardarSucursal.setBackground(new Color(46, 204, 113)); // Verde
        btnGuardarSucursal.setForeground(Color.WHITE);
        panel.add(btnGuardarSucursal);

        btnLimpiarSucursal = new JButton("Limpiar Campos");
        btnLimpiarSucursal.setBounds(20, 315, 250, 35);
        btnLimpiarSucursal.setBackground(new Color(149, 165, 166)); // Gris
        btnLimpiarSucursal.setForeground(Color.WHITE);
        panel.add(btnLimpiarSucursal);

        btnEliminarSucursal = new JButton("Eliminar Seleccionada");
        btnEliminarSucursal.setBounds(20, 360, 250, 35);
        btnEliminarSucursal.setBackground(new Color(231, 76, 60)); // Rojo
        btnEliminarSucursal.setForeground(Color.WHITE);
        panel.add(btnEliminarSucursal);

        String[] columnas = {"ID", "Nombre", "Contacto", "Ubicación"};
        modeloSucursales = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaSucursales = new JTable(modeloSucursales);
        tablaSucursales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tablaSucursales);
        scrollPane.setBounds(300, 20, 500, 480);
        panel.add(scrollPane);

        tablaSucursales.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaSucursales.getSelectedRow() != -1) {
                int fila = tablaSucursales.getSelectedRow();
                txtNombreSucursal.setText(tablaSucursales.getValueAt(fila, 1).toString());
                txtContactoSucursal.setText(tablaSucursales.getValueAt(fila, 2).toString());
                txtUbicacionSucursal.setText(tablaSucursales.getValueAt(fila, 3).toString());
            }
        });

        btnLimpiarSucursal.addActionListener(e -> limpiarFormularioSucursal());

        return panel;
    }

    public String getNombreSucursal() {
        return txtNombreSucursal.getText().trim();
    }

    public String getContactoSucursal() {
        return txtContactoSucursal.getText().trim();
    }

    public String getUbicacionSucursal() {
        return txtUbicacionSucursal.getText().trim();
    }

    public int getIdSucursalSeleccionada() {
        int fila = tablaSucursales.getSelectedRow();
        if (fila == -1) {
            return 0;
        }
        return (int) tablaSucursales.getValueAt(fila, 0);
    }

    public void limpiarFormularioSucursal() {
        txtNombreSucursal.setText("");
        txtContactoSucursal.setText("");
        txtUbicacionSucursal.setText("");
        tablaSucursales.clearSelection();
    }

    public void actualizarTablaSucursales(List<Sucursal> lista) {
        modeloSucursales.setRowCount(0);
        for (Sucursal s : lista) {
            modeloSucursales.addRow(new Object[]{
                    s.getSucursal_id(),
                    s.getNombre(),
                    s.getContacto(),
                    s.getUbicacion()
            });
        }
    }

    public void addGuardarSucursalListener(ActionListener listener) {
        btnGuardarSucursal.addActionListener(listener);
    }

    public void addEliminarSucursalListener(ActionListener listener) {
        btnEliminarSucursal.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
