package org.practica1.views;

import org.practica1.models.Configuracion;
import org.practica1.models.Sucursal;
import org.practica1.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SuperAdminFrame extends JFrame {

    // Variables que sirven para la creacion y modificacion de una sucursal.
    private JTextField txtNombreSucursal;
    private JTextField txtContactoSucursal;
    private JTextField txtUbicacionSucursal;
    private JButton btnGuardarSucursal;
    private JButton btnEliminarSucursal;
    private JButton btnLimpiarSucursal;
    private JTable tablaSucursales;
    private DefaultTableModel modeloSucursales;

    // Variables que sirven para la ceracion y modificacion de un usuario.
    private JTextField txtNombreUsuario;
    private JTextField txtEmailUsuario;
    private JPasswordField txtContrasenaUsuario;
    private JComboBox<String> cbxRolUsuario;
    private JComboBox<Sucursal> cbxSucursalUsuario;
    private JButton btnGuardarUsuario;
    private JButton btnEliminarUsuario;
    private JButton btnLimpiarUsuario;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloUsuarios;

    // Variables que sirven para modificar los valores con los que trabaja en el sistema
    private JSpinner jsTiempoPreparacion;
    private JLabel lblId;
    private JSpinner jsDificultadNivel;
    private JSpinner jsPunteoMinimo;
    private JSpinner jsCompleto;
    private JSpinner jsCompletoOptimo;
    private JSpinner jsCompletoEficiente;
    private JSpinner jsCancelado;
    private JSpinner jsNoEntregado;
    private JButton btnGuardarConfig;

    private Font titulo = new Font("Arial", Font.BOLD, 18);
    private Font principal = new Font("Helvetica", Font.BOLD, 14);
    private Font secundario = new Font("Helvetica", Font.PLAIN, 13);
    private Color textColor = Color.LIGHT_GRAY;

    public SuperAdminFrame() {
        setTitle("Pizza Express Tycoon - Panel de Super Administrador");
        setSize(950, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        iniciarComponentes();
    }

    private void iniciarComponentes() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(principal);

        JPanel panelSucursal = crearPanelSucursal();
        tabbedPane.addTab("Gestión de sucursales", null, panelSucursal, "Administrar las tiendas");

        JPanel panelUsuarios = crearPanelUsuarios();
        tabbedPane.addTab("Gestión de Usuarios", null, panelUsuarios, "Administrar empleados y jugadores");

        JPanel panelConfig = crearPanelConfig();
        tabbedPane.addTab("Gestión del sistema", null, panelConfig, "Administrar el sistema");

        add(tabbedPane);
    }

    // Toda la parte de la vista que esta relacionado con sucursal
    private JPanel crearPanelSucursal() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(30, 30, 30));

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

        //tabla donde se muestran las sucursales
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
        scrollPane.setBounds(300, 20, 600, 480);
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

    public JButton getBtnGuardarSucursal() {
        return btnGuardarSucursal;
    }

    public JButton getBtnEliminarSucursal() {
        return btnEliminarSucursal;
    }

    public int getIdSucursalSeleccionada() {
        int fila = tablaSucursales.getSelectedRow();
        return (fila == -1) ? 0 : (int) tablaSucursales.getValueAt(fila, 0);
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

    // Toda la parte de la vista que esta relacionado con Usuario
    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(30, 30, 30));

        JLabel lblTitulo = new JLabel("Datos del Usuario");
        lblTitulo.setFont(titulo);
        lblTitulo.setForeground(new Color(241, 196, 15));
        lblTitulo.setBounds(20, 10, 300, 30);
        panel.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(principal);
        lblNombre.setForeground(textColor);
        lblNombre.setBounds(20, 50, 250, 20);
        panel.add(lblNombre);

        txtNombreUsuario = new JTextField();
        txtNombreUsuario.setFont(secundario);
        txtNombreUsuario.setBounds(20, 70, 250, 30);
        panel.add(txtNombreUsuario);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(principal);
        lblEmail.setForeground(textColor);
        lblEmail.setBounds(20, 110, 250, 20);
        panel.add(lblEmail);

        txtEmailUsuario = new JTextField();
        txtEmailUsuario.setFont(secundario);
        txtEmailUsuario.setBounds(20, 130, 250, 30);
        panel.add(txtEmailUsuario);

        JLabel lblCon = new JLabel("Contraseña:");
        lblCon.setFont(principal);
        lblCon.setForeground(textColor);
        lblCon.setBounds(20, 170, 250, 20);
        panel.add(lblCon);

        txtContrasenaUsuario = new JPasswordField();
        txtContrasenaUsuario.setFont(secundario);
        txtContrasenaUsuario.setBounds(20, 190, 250, 30);
        panel.add(txtContrasenaUsuario);

        JLabel lblRol = new JLabel("Rol del sistema:");
        lblRol.setFont(principal);
        lblRol.setForeground(textColor);
        lblRol.setBounds(20, 230, 250, 20);
        panel.add(lblRol);

        String[] roles = {"SUPER_ADMIN", "ADMIN_TIENDA", "JUGADOR"};
        cbxRolUsuario = new JComboBox<>(roles);
        cbxRolUsuario.setFont(secundario);
        cbxRolUsuario.setBounds(20, 250, 250, 30);
        panel.add(cbxRolUsuario);

        JLabel lblSucursal = new JLabel("Asignar Sucursal");
        lblSucursal.setFont(principal);
        lblSucursal.setForeground(textColor);
        lblSucursal.setBounds(20, 290, 250, 20);
        panel.add(lblSucursal);

        cbxSucursalUsuario = new JComboBox<>();
        cbxSucursalUsuario.setFont(secundario);
        cbxSucursalUsuario.setBounds(20, 310, 250, 30);
        panel.add(cbxSucursalUsuario);

        btnGuardarUsuario = new JButton("Guardar / Actualizar");
        btnGuardarUsuario.setFont(principal);
        btnGuardarUsuario.setBounds(20, 360, 250, 35);
        btnGuardarUsuario.setBackground(new Color(46, 204, 113));
        btnGuardarUsuario.setForeground(Color.WHITE);
        panel.add(btnGuardarUsuario);

        btnEliminarUsuario = new JButton("Desactivar/Activar Seleccionado");
        btnEliminarUsuario.setFont(principal);
        btnEliminarUsuario.setBounds(20, 405, 250, 35);
        btnEliminarUsuario.setBackground(new Color(231, 76, 60));
        btnEliminarUsuario.setForeground(Color.WHITE);
        panel.add(btnEliminarUsuario);

        btnLimpiarUsuario = new JButton("Limpiar Campos");
        btnLimpiarUsuario.setFont(principal);
        btnLimpiarUsuario.setBounds(20, 450, 250, 35);
        btnLimpiarUsuario.setBackground(new Color(149, 165, 166));
        btnLimpiarUsuario.setForeground(Color.WHITE);
        panel.add(btnLimpiarUsuario);

        btnLimpiarUsuario.addActionListener(e -> limpiarFormularioUsuario());

        //Tabla donde se van ha mostrar los usuarios
        String[] columnas = {"Id", "Nombre", "Email", "Rol", "Id Suc", "Estado"};
        modeloUsuarios = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaUsuarios = new JTable(modeloUsuarios);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setBounds(300, 20, 600, 480);
        panel.add(scroll);

        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaUsuarios.getSelectedRow() != -1) {
                int fila = tablaUsuarios.getSelectedRow();
                txtNombreUsuario.setText(tablaUsuarios.getValueAt(fila, 1).toString());
                txtEmailUsuario.setText(tablaUsuarios.getValueAt(fila, 2).toString());
                cbxRolUsuario.setSelectedItem(tablaUsuarios.getValueAt(fila, 3).toString());
                txtContrasenaUsuario.setText("");

                Object valorSucursal = tablaUsuarios.getValueAt(fila, 4);
                int idSucursalTabla = 0;

                if (valorSucursal != null) {
                    idSucursalTabla = (int) valorSucursal;
                }
                for (int i = 0; i < cbxSucursalUsuario.getItemCount(); i++) {
                    Sucursal s = cbxSucursalUsuario.getItemAt(i);
                    if (s.getSucursal_id() == idSucursalTabla) {
                        cbxSucursalUsuario.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });


        return panel;
    }

    public String getNombreUsuario() {
        return txtNombreUsuario.getText().trim();
    }

    public String getEmailUsuario() {
        return txtEmailUsuario.getText().trim();
    }

    public String getContrasena() {
        return new String(txtContrasenaUsuario.getPassword());
    }

    public String getRolUsuario() {
        return cbxRolUsuario.getSelectedItem().toString();
    }

    public Sucursal getSucursalUsuarioSeleccionada() {
        return (Sucursal) cbxSucursalUsuario.getSelectedItem();
    }

    public int getIdUsuarioTablaSeleccionado() {
        int fila = tablaUsuarios.getSelectedRow();
        return (fila == -1) ? 0 : (int) tablaUsuarios.getValueAt(fila, 0);
    }

    public JButton getBtnGuardarUsuario() {
        return btnGuardarUsuario;
    }

    public JButton getBtnEliminarUsuario() {
        return btnEliminarUsuario;
    }

    public void limpiarFormularioUsuario() {
        txtNombreUsuario.setText("");
        txtEmailUsuario.setText("");
        txtContrasenaUsuario.setText("");
        cbxRolUsuario.setSelectedIndex(0);
        if (cbxSucursalUsuario.getItemCount() > 0) cbxSucursalUsuario.setSelectedIndex(0);
        tablaUsuarios.clearSelection();
    }

    public void cargarComboSucursales(List<Sucursal> sucursales) {
        cbxSucursalUsuario.removeAllItems();

        cbxSucursalUsuario.addItem(new Sucursal("Sin sucursal", "", ""));
        for (Sucursal s : sucursales) {
            cbxSucursalUsuario.addItem(s);
        }
    }

    public void actualizarTablaUsuarios(List<Usuario> lista) {
        modeloUsuarios.setRowCount(0);
        for (Usuario u : lista) {
            String estadoLegible = u.isEstado() ? "Activo" : "Inactivo";
            modeloUsuarios.addRow(new Object[]{
                    u.getUsuario_id(),
                    u.getNombre(),
                    u.getEmail(),
                    u.getRol(),
                    u.getSucursal_id(),
                    estadoLegible
            });
        }
    }

    private JPanel crearPanelConfig() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(30, 30, 30));

        JLabel lblTitulo = new JLabel("Configuracion del sistema");
        lblTitulo.setFont(titulo);
        lblTitulo.setBounds(300, 30, 850, 30);
        lblTitulo.setForeground(new Color(241, 196, 15));
        panel.add(lblTitulo);

        lblId = new JLabel("0");
        lblId.setVisible(false);
        panel.add(lblId);

        int xIzq = 80;
        int anchoCol = 320;

        JLabel lblTiempoPreparacion = new JLabel("Tiempo de preparación base (seg):");
        lblTiempoPreparacion.setFont(principal);
        lblTiempoPreparacion.setForeground(textColor);
        lblTiempoPreparacion.setBounds(xIzq, 90, anchoCol, 20);
        panel.add(lblTiempoPreparacion);

        jsTiempoPreparacion = new JSpinner(new SpinnerNumberModel(60, 0, 10000, 1));
        jsTiempoPreparacion.setFont(secundario);
        jsTiempoPreparacion.setBounds(xIzq, 110, anchoCol, 35);
        panel.add(jsTiempoPreparacion);

        JLabel lblDificultadNivel = new JLabel("Dificultad (Tiempo restado por nivel):");
        lblDificultadNivel.setFont(principal);
        lblDificultadNivel.setForeground(textColor);
        lblDificultadNivel.setBounds(xIzq, 170, anchoCol, 20);
        panel.add(lblDificultadNivel);

        jsDificultadNivel = new JSpinner(new SpinnerNumberModel(10, 0, 10000, 1));
        jsDificultadNivel.setFont(secundario);
        jsDificultadNivel.setBounds(xIzq, 190, anchoCol, 35);
        panel.add(jsDificultadNivel);

        JLabel lblPuntoMinimo = new JLabel("Punteo mínimo para subir de nivel:");
        lblPuntoMinimo.setFont(principal);
        lblPuntoMinimo.setForeground(textColor);
        lblPuntoMinimo.setBounds(xIzq, 250, anchoCol, 20);
        panel.add(lblPuntoMinimo);

        jsPunteoMinimo = new JSpinner(new SpinnerNumberModel(600, 0, 10000, 1));
        jsPunteoMinimo.setFont(secundario);
        jsPunteoMinimo.setBounds(xIzq, 270, anchoCol, 35);
        panel.add(jsPunteoMinimo);

        JLabel lblCompleto = new JLabel("Puntos al completar un pedido normal:");
        lblCompleto.setFont(principal);
        lblCompleto.setForeground(textColor);
        lblCompleto.setBounds(xIzq, 330, anchoCol, 20);
        panel.add(lblCompleto);

        jsCompleto = new JSpinner(new SpinnerNumberModel(100, 0, 10000, 1));
        jsCompleto.setFont(secundario);
        jsCompleto.setBounds(xIzq, 350, anchoCol, 35);
        panel.add(jsCompleto);

        int xDer = 450;

        JLabel lblCompletoOptimo = new JLabel("Puntos al completar antes del límite:");
        lblCompletoOptimo.setFont(principal);
        lblCompletoOptimo.setForeground(textColor);
        lblCompletoOptimo.setBounds(xDer, 90, anchoCol, 20);
        panel.add(lblCompletoOptimo);

        jsCompletoOptimo = new JSpinner(new SpinnerNumberModel(50, 0, 10000, 1));
        jsCompletoOptimo.setFont(secundario);
        jsCompletoOptimo.setBounds(xDer, 110, anchoCol, 35);
        panel.add(jsCompletoOptimo);

        JLabel lblCompletoEficiente = new JLabel("Bonus por velocidad (Multiplicador %):");
        lblCompletoEficiente.setFont(principal);
        lblCompletoEficiente.setForeground(textColor);
        lblCompletoEficiente.setBounds(xDer, 170, anchoCol, 20);
        panel.add(lblCompletoEficiente);

        jsCompletoEficiente = new JSpinner(new SpinnerNumberModel(0.5, 0.0, 100.0, 0.1));
        jsCompletoEficiente.setFont(secundario);
        jsCompletoEficiente.setBounds(xDer, 190, anchoCol, 35);
        panel.add(jsCompletoEficiente);

        JLabel lblCancelado = new JLabel("Puntos perdidos al cancelar un pedido:");
        lblCancelado.setFont(principal);
        lblCancelado.setForeground(textColor);
        lblCancelado.setBounds(xDer, 250, anchoCol, 20);
        panel.add(lblCancelado);

        jsCancelado = new JSpinner(new SpinnerNumberModel(30, 0, 10000, 1));
        jsCancelado.setFont(secundario);
        jsCancelado.setBounds(xDer, 270, anchoCol, 35);
        panel.add(jsCancelado);

        JLabel lblNoEntregado = new JLabel("Puntos perdidos por no entregar:");
        lblNoEntregado.setFont(principal);
        lblNoEntregado.setForeground(textColor);
        lblNoEntregado.setBounds(xDer, 330, anchoCol, 20);
        panel.add(lblNoEntregado);

        jsNoEntregado = new JSpinner(new SpinnerNumberModel(50, 0, 10000, 1));
        jsNoEntregado.setFont(secundario);
        jsNoEntregado.setBounds(xDer, 350, anchoCol, 35);
        panel.add(jsNoEntregado);

        btnGuardarConfig = new JButton("Guardar Reglas del Juego");
        btnGuardarConfig.setFont(new Font("Helvetica", Font.BOLD, 14));
        btnGuardarConfig.setBackground(new Color(46, 204, 113));
        btnGuardarConfig.setForeground(Color.WHITE);
        btnGuardarConfig.setFocusPainted(false);
        btnGuardarConfig.setBounds(300, 430, 225, 45);
        panel.add(btnGuardarConfig);

        return panel;
    }

    public void cargarConfiguracion(Configuracion configuracion) {
        jsTiempoPreparacion.setValue(configuracion.getTiempo_preparacion());
        jsDificultadNivel.setValue(configuracion.getDificultad_nivel());
        jsPunteoMinimo.setValue(configuracion.getPunteo_minimo());
        jsCompleto.setValue(configuracion.getCompleto());
        jsCompletoOptimo.setValue(configuracion.getCompleto_optimo());
        jsCompletoEficiente.setValue(configuracion.getCompleto_eficiente());
        jsCancelado.setValue(configuracion.getCancelado());
        jsNoEntregado.setValue(configuracion.getNo_entregado());
        lblId.setText(String.valueOf(configuracion.getConfiguracion_id()));
    }

    public int getJsTiempoPreparacion() {
        return (Integer) jsTiempoPreparacion.getValue();
    }

    public int getJsDificultadNivel() {
        return (Integer) jsDificultadNivel.getValue();
    }

    public int getJsPunteoMinimo() {
        return (Integer) jsPunteoMinimo.getValue();
    }

    public int getJsCompleto() {
        return (Integer) jsCompleto.getValue();
    }

    public int getJsCompletoOptimo() {
        return (Integer) jsCompletoOptimo.getValue();
    }

    public double getJsCompletoEficiente() {
        return (Double) jsCompletoEficiente.getValue();
    }

    public int getJsCancelado() {
        return (Integer) jsCancelado.getValue();
    }

    public int getJsNoEntregado() {
        return (Integer) jsNoEntregado.getValue();
    }

    public int getLblId() {
        return Integer.parseInt(lblId.getText());
    }

    public JButton getBtnGuardarConfig() {
        return btnGuardarConfig;
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
