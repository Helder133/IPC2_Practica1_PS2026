package org.practica1.views;

import org.practica1.models.Ingrediente;
import org.practica1.models.Producto;
import org.practica1.models.SucursalIngrediente;
import org.practica1.models.SucursalProducto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminTiendaFrame extends JFrame {
    // INGREDIENTES ---
    private JTextField txtNombreIngrediente;
    private JButton btnGuardarIngrediente;
    private JButton btnEliminarIngrediente;
    private JButton btnLimpiarIngrediente;
    private JTable tablaIngredientes;
    private DefaultTableModel modeloIngredientes;

    // PRODUCTOS (PIZZAS) ---
    private JTextField txtNombreProducto;
    private JSpinner jsTiempoPreparacion;
    private JButton btnGuardarProducto;
    private JButton btnEliminarProducto;
    private JButton btnLimpiarProducto;
    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;

    // RECETAS (PRODUCTO_INGREDIENTE) ---
    private JComboBox<Producto> cbxProductoReceta;
    private JComboBox<Ingrediente> cbxIngredienteReceta;
    private JButton btnAgregarIngredienteReceta;
    private JButton btnQuitarIngredienteReceta;
    private JTable tablaReceta;
    private DefaultTableModel modeloReceta;

    // PRODUCTOS LOCALES ---
    private JComboBox<Producto> cbxProductoLocal;
    private JButton btnAgregarProductoLocal;
    private JButton btnCambiarEstadoProducto;
    private JButton btnEliminarProductoLocal;
    private JTable tablaProductosLocales;
    private DefaultTableModel modeloProductosLocales;

    //INGREDIENTES LOCALES --
    private JComboBox<Ingrediente> cbxIngredienteLocal;
    private JButton btnAgregarIngredienteLocal;
    private JButton btnCambiarEstadoIngrediente;
    private JButton btnEliminarIngredienteLocal;
    private JTable tablaIngredientesLocales;
    private DefaultTableModel modeloIngredientesLocales;

    private JMenuItem itemCerrarSesion;

    private Font titulo = new Font("Arial", Font.BOLD, 18);
    private Font principal = new Font("Helvetica", Font.BOLD, 14);
    private Font secundario = new Font("Helvetica", Font.PLAIN, 13);
    private Color textColor = Color.LIGHT_GRAY;

    public AdminTiendaFrame() {
        setTitle("Pizza Express Tycoon - Administrador de Tienda (Gestión Global)");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(principal);

        tabbedPane.addTab("1. Catálogo de Ingredientes", null, crearPanelIngredientes(), "Crear ingredientes base");
        tabbedPane.addTab("2. Catálogo de Pizzas", null, crearPanelProductos(), "Crear pizzas base");
        tabbedPane.addTab("3. Armar Recetas", null, crearPanelRecetas(), "Asignar ingredientes a las pizzas");
        tabbedPane.addTab("4. Menú Local", null, crearPanelMenuLocal(), "Pizzas de esta tienda");
        tabbedPane.addTab("5. Stock Local", null, crearPanelStockLocal(), "Ingredientes de esta tienda");

        this.add(tabbedPane);
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCuenta = new JMenu("Cuenta");
        itemCerrarSesion = new JMenuItem("Cerrar sesión");
        itemCerrarSesion.setForeground(Color.RED);
        menuCuenta.add(itemCerrarSesion);
        menuBar.add(menuCuenta);
        this.setJMenuBar(menuBar);

    }

    public JMenuItem getItemCerrarSesion() {
        return itemCerrarSesion;
    }

    // INGREDIENTES
    private JPanel crearPanelIngredientes() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        JLabel lblTitulo = new JLabel("Gestión de Ingredientes Globales");
        lblTitulo.setFont(titulo);
        lblTitulo.setForeground(new Color(241, 196, 15));
        lblTitulo.setBounds(20, 20, 300, 30);
        panel.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre del Ingrediente:");
        lblNombre.setFont(principal);
        lblNombre.setForeground(Color.LIGHT_GRAY);
        lblNombre.setBounds(20, 70, 250, 20);
        panel.add(lblNombre);

        txtNombreIngrediente = new JTextField();
        txtNombreIngrediente.setBounds(20, 90, 250, 30);
        txtNombreIngrediente.setFont(secundario);
        panel.add(txtNombreIngrediente);

        btnGuardarIngrediente = new JButton("Guardar Ingrediente");
        btnGuardarIngrediente.setBackground(new Color(46, 204, 113));
        btnGuardarIngrediente.setForeground(Color.WHITE);
        btnGuardarIngrediente.setBounds(20, 140, 250, 35);
        panel.add(btnGuardarIngrediente);

        btnLimpiarIngrediente = new JButton("Limpiar Selección");
        btnLimpiarIngrediente.setBackground(new Color(149, 165, 166));
        btnLimpiarIngrediente.setForeground(Color.WHITE);
        btnLimpiarIngrediente.setBounds(20, 185, 250, 35);
        panel.add(btnLimpiarIngrediente);

        btnEliminarIngrediente = new JButton("Eliminar Ingrediente");
        btnEliminarIngrediente.setBackground(new Color(231, 76, 60));
        btnEliminarIngrediente.setForeground(Color.WHITE);
        btnEliminarIngrediente.setBounds(20, 230, 250, 35);
        panel.add(btnEliminarIngrediente);

        modeloIngredientes = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaIngredientes = new JTable(modeloIngredientes);
        tablaIngredientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaIngredientes);
        scroll.setBounds(330, 20, 550, 480);
        panel.add(scroll);

        // Evento visual para llenar el form al hacer clic
        tablaIngredientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaIngredientes.getSelectedRow() != -1) {
                txtNombreIngrediente.setText(tablaIngredientes.getValueAt(tablaIngredientes.getSelectedRow(), 1).toString());
            }
        });

        btnLimpiarIngrediente.addActionListener(e -> limpiarFormIngrediente());

        return panel;
    }

    public String getNombreIngrediente() {
        return txtNombreIngrediente.getText().trim();
    }

    public int getIdIngredienteSeleccionado() {
        int fila = tablaIngredientes.getSelectedRow();
        return (fila == -1) ? 0 : (int) tablaIngredientes.getValueAt(fila, 0);
    }

    public JButton getBtnGuardarIngrediente() {
        return btnGuardarIngrediente;
    }

    public JButton getBtnEliminarIngrediente() {
        return btnEliminarIngrediente;
    }

    public void actualizarTablaIngrediente(List<Ingrediente> list) {
        modeloIngredientes.setRowCount(0);
        for (Ingrediente i : list) {
            modeloIngredientes.addRow(new Object[]{
                    i.getIngredienteId(),
                    i.getNombre()
            });
        }
    }

    public void limpiarFormIngrediente() {
        txtNombreIngrediente.setText("");
        tablaIngredientes.clearSelection();
    }

    // PRODUCTOS (PIZZAS)
    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        JLabel lblTitulo = new JLabel("Gestión de Pizzas Base");
        lblTitulo.setFont(titulo);
        lblTitulo.setForeground(new Color(241, 196, 15));
        lblTitulo.setBounds(20, 20, 300, 30);
        panel.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre de la Pizza:");
        lblNombre.setFont(principal);
        lblNombre.setForeground(Color.LIGHT_GRAY);
        lblNombre.setBounds(20, 70, 250, 20);
        panel.add(lblNombre);

        txtNombreProducto = new JTextField();
        txtNombreProducto.setBounds(20, 90, 250, 30);
        txtNombreProducto.setFont(secundario);
        panel.add(txtNombreProducto);

        JLabel lblTiempo = new JLabel("Tiempo Base Preparación (seg):");
        lblTiempo.setFont(principal);
        lblTiempo.setForeground(Color.LIGHT_GRAY);
        lblTiempo.setBounds(20, 130, 250, 20);
        panel.add(lblTiempo);

        jsTiempoPreparacion = new JSpinner(new SpinnerNumberModel(60, 1, 3600, 1));
        jsTiempoPreparacion.setBounds(20, 150, 250, 30);
        jsTiempoPreparacion.setFont(secundario);
        panel.add(jsTiempoPreparacion);

        btnGuardarProducto = new JButton("Guardar Pizza");
        btnGuardarProducto.setBackground(new Color(46, 204, 113));
        btnGuardarProducto.setForeground(Color.WHITE);
        btnGuardarProducto.setBounds(20, 200, 250, 35);
        panel.add(btnGuardarProducto);

        btnLimpiarProducto = new JButton("Limpiar Selección");
        btnLimpiarProducto.setBackground(new Color(149, 165, 166));
        btnLimpiarProducto.setForeground(Color.WHITE);
        btnLimpiarProducto.setBounds(20, 245, 250, 35);
        panel.add(btnLimpiarProducto);

        btnEliminarProducto = new JButton("Eliminar Pizza");
        btnEliminarProducto.setBackground(new Color(231, 76, 60));
        btnEliminarProducto.setForeground(Color.WHITE);
        btnEliminarProducto.setBounds(20, 290, 250, 35);
        panel.add(btnEliminarProducto);

        modeloProductos = new DefaultTableModel(new String[]{"ID", "Nombre Pizza", "Tiempo Base (seg)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProductos = new JTable(modeloProductos);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBounds(330, 20, 550, 480);
        panel.add(scroll);

        // Evento visual
        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaProductos.getSelectedRow() != -1) {
                int fila = tablaProductos.getSelectedRow();
                txtNombreProducto.setText(tablaProductos.getValueAt(fila, 1).toString());
                jsTiempoPreparacion.setValue(Integer.parseInt(tablaProductos.getValueAt(fila, 2).toString()));
            }
        });

        btnLimpiarProducto.addActionListener(e -> limpiarFormProducto());

        return panel;
    }

    // --- Productos ---
    public String getNombreProducto() {
        return txtNombreProducto.getText().trim();
    }

    public int getTiempoPreparacionProducto() {
        return (Integer) jsTiempoPreparacion.getValue();
    }

    public void setJsTiempoPreparacion(int tiempo) {
        this.jsTiempoPreparacion.setValue(tiempo);
    }

    public int getIdProductoSeleccionado() {
        int fila = tablaProductos.getSelectedRow();
        return (fila == -1) ? 0 : (int) tablaProductos.getValueAt(fila, 0);
    }

    public JButton getBtnGuardarProducto() {
        return btnGuardarProducto;
    }

    public JButton getBtnEliminarProducto() {
        return btnEliminarProducto;
    }

    public void actualizarTablaProducto(List<Producto> list) {
        modeloProductos.setRowCount(0);
        for (Producto p : list) {
            modeloProductos.addRow(new Object[]{
                    p.getProductoID(),
                    p.getNombre(),
                    p.getTiempoBaseDePreparacion()});
        }
    }

    public void limpiarFormProducto() {
        txtNombreProducto.setText("");
        jsTiempoPreparacion.setValue(60);
        tablaProductos.clearSelection();
    }

    //RECETAS
    private JPanel crearPanelRecetas() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        JLabel lblTitulo = new JLabel("Armar Receta (Asignar Ingredientes)");
        lblTitulo.setFont(titulo);
        lblTitulo.setForeground(new Color(241, 196, 15));
        lblTitulo.setBounds(20, 20, 350, 30);
        panel.add(lblTitulo);

        JLabel lblProd = new JLabel("1. Seleccione la Pizza:");
        lblProd.setFont(principal);
        lblProd.setForeground(Color.LIGHT_GRAY);
        lblProd.setBounds(20, 70, 250, 20);
        panel.add(lblProd);

        cbxProductoReceta = new JComboBox<>();
        cbxProductoReceta.setBounds(20, 90, 250, 30);
        cbxProductoReceta.setFont(secundario);
        panel.add(cbxProductoReceta);

        JLabel lblIng = new JLabel("2. Seleccione un Ingrediente:");
        lblIng.setFont(principal);
        lblIng.setForeground(Color.LIGHT_GRAY);
        lblIng.setBounds(20, 140, 250, 20);
        panel.add(lblIng);

        cbxIngredienteReceta = new JComboBox<>();
        cbxIngredienteReceta.setBounds(20, 160, 250, 30);
        cbxIngredienteReceta.setFont(secundario);
        panel.add(cbxIngredienteReceta);

        btnAgregarIngredienteReceta = new JButton("Agregar a la Receta >>");
        btnAgregarIngredienteReceta.setBackground(new Color(41, 128, 185));
        btnAgregarIngredienteReceta.setForeground(Color.WHITE);
        btnAgregarIngredienteReceta.setBounds(20, 210, 250, 35);
        panel.add(btnAgregarIngredienteReceta);

        btnQuitarIngredienteReceta = new JButton("<< Quitar de la Receta");
        btnQuitarIngredienteReceta.setBackground(new Color(231, 76, 60));
        btnQuitarIngredienteReceta.setForeground(Color.WHITE);
        btnQuitarIngredienteReceta.setBounds(20, 255, 250, 35);
        panel.add(btnQuitarIngredienteReceta);

        // La tabla mostrará los ingredientes que pertenecen a la pizza seleccionada en el ComboBox superior
        modeloReceta = new DefaultTableModel(new String[]{"ID Ingrediente", "Nombre Ingrediente"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaReceta = new JTable(modeloReceta);
        JScrollPane scroll = new JScrollPane(tablaReceta);
        scroll.setBounds(380, 20, 550, 480);
        panel.add(scroll);

        return panel;
    }

    // --- Recetas ---
    public JComboBox<Producto> getCbxProductoReceta() {
        return cbxProductoReceta;
    }

    public JButton getBtnAgregarIngredienteReceta() {
        return btnAgregarIngredienteReceta;
    }

    public JButton getBtnQuitarIngredienteReceta() {
        return btnQuitarIngredienteReceta;
    }

    public void actualizarTablaReceta(List<Ingrediente> list) {
        modeloReceta.setRowCount(0);
        for (Ingrediente i : list) {
            modeloReceta.addRow(new Object[]{
                    i.getIngredienteId(),
                    i.getNombre()
            });
        }
    }

    public void cargarCombosProducto(List<Producto> list) {
        cbxProductoReceta.removeAllItems();
        for (Producto p : list) {
            cbxProductoReceta.addItem(p);
        }
    }

    public void cargarCombosIngrediente(List<Ingrediente> list) {
        cbxIngredienteReceta.removeAllItems();
        for (Ingrediente i : list) {
            cbxIngredienteReceta.addItem(i);
        }
    }

    public Producto productoSeleccionado() {
        return (Producto) cbxProductoReceta.getSelectedItem();
    }

    public Ingrediente ingredienteSeleccionado() {
        return (Ingrediente) cbxIngredienteReceta.getSelectedItem();
    }

    public int getIdProductoIngredienteSeleccionado() {
        int fila = tablaReceta.getSelectedRow();
        return (fila == -1) ? 0 : (int) tablaReceta.getValueAt(fila, 0);
    }

    //Producto locales
    private JPanel crearPanelMenuLocal() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        JLabel lblTitulo = new JLabel("Menú de mi Sucursal");
        lblTitulo.setFont(titulo);
        lblTitulo.setForeground(new Color(241, 196, 15));
        lblTitulo.setBounds(20, 20, 300, 30);
        panel.add(lblTitulo);

        JLabel lblProd = new JLabel("Añadir Pizza del Catálogo Global:");
        lblProd.setFont(principal);
        lblProd.setForeground(Color.LIGHT_GRAY);
        lblProd.setBounds(20, 70, 250, 20);
        panel.add(lblProd);

        cbxProductoLocal = new JComboBox<>();
        cbxProductoLocal.setBounds(20, 90, 250, 30);
        cbxProductoLocal.setFont(secundario);
        panel.add(cbxProductoLocal);

        btnAgregarProductoLocal = new JButton("Agregar al Menú Local");
        btnAgregarProductoLocal.setBackground(new Color(46, 204, 113));
        btnAgregarProductoLocal.setForeground(Color.WHITE);
        btnAgregarProductoLocal.setBounds(20, 140, 250, 35);
        btnAgregarProductoLocal.setFont(principal);
        panel.add(btnAgregarProductoLocal);

        btnCambiarEstadoProducto = new JButton("Activar / Desactivar Venta");
        btnCambiarEstadoProducto.setBackground(new Color(243, 156, 18)); // Naranja
        btnCambiarEstadoProducto.setForeground(Color.WHITE);
        btnCambiarEstadoProducto.setBounds(20, 185, 250, 35);
        btnCambiarEstadoProducto.setFont(principal);
        panel.add(btnCambiarEstadoProducto);

        btnEliminarProductoLocal = new JButton("Eliminar");
        btnEliminarProductoLocal.setBackground(new Color(231, 76, 60)); // Rojo
        btnEliminarProductoLocal.setForeground(Color.WHITE);
        btnEliminarProductoLocal.setBounds(20, 230, 250, 35);
        btnEliminarProductoLocal.setFont(principal);
        panel.add(btnEliminarProductoLocal);

        modeloProductosLocales = new DefaultTableModel(new String[]{"ID Producto", "Nombre Pizza", "Estado (Activo)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProductosLocales = new JTable(modeloProductosLocales);
        tablaProductosLocales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaProductosLocales);
        scroll.setBounds(300, 20, 550, 480);
        panel.add(scroll);

        return panel;
    }

    public void cargarCombosProductoLocal (List<Producto> list) {
        cbxProductoLocal.removeAllItems();
        for (Producto p : list) {
            cbxProductoLocal.addItem(p);
        }
    }

    public Producto productoLocalSeleccionado() {
        return (Producto) cbxProductoLocal.getSelectedItem();
    }

    public void actualizarTablaProductoLocal(List<SucursalProducto> list) {
        modeloProductosLocales.setRowCount(0);
        for (SucursalProducto p : list) {
            modeloProductosLocales.addRow(new Object[]{
                    p.getProductoId(),
                    p.getNombreProducto(),
                    p.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }

    public int getIdProductoLocalSeleccionado() {
        int fila = tablaProductosLocales.getSelectedRow();
        return (fila == -1) ? 0 : (int) tablaProductosLocales.getValueAt(fila, 0);
    }

    public JButton getBtnAgregarProductoLocal() {
        return btnAgregarProductoLocal;
    }

    public JButton getBtnCambiarEstadoProducto() {
        return btnCambiarEstadoProducto;
    }

    public JButton getBtnEliminarProductoLocal() {
        return btnEliminarProductoLocal;
    }

    //Ingredientes Locales
    private JPanel crearPanelStockLocal() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        JLabel lblTitulo = new JLabel("Inventario de mi Sucursal");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(241, 196, 15));
        lblTitulo.setBounds(20, 20, 300, 30);
        panel.add(lblTitulo);

        JLabel lblIng = new JLabel("Añadir Ingrediente del Catálogo:");
        lblIng.setFont(new Font("Helvetica", Font.BOLD, 14));
        lblIng.setForeground(Color.LIGHT_GRAY);
        lblIng.setBounds(20, 70, 250, 20);
        panel.add(lblIng);

        cbxIngredienteLocal = new JComboBox<>();
        cbxIngredienteLocal.setBounds(20, 90, 250, 30);
        panel.add(cbxIngredienteLocal);

        btnAgregarIngredienteLocal = new JButton("Agregar al Stock Local");
        btnAgregarIngredienteLocal.setBackground(new Color(46, 204, 113));
        btnAgregarIngredienteLocal.setForeground(Color.WHITE);
        btnAgregarIngredienteLocal.setBounds(20, 140, 250, 35);
        panel.add(btnAgregarIngredienteLocal);

        btnCambiarEstadoIngrediente = new JButton("Hay / No Hay Stock");
        btnCambiarEstadoIngrediente.setBackground(new Color(243, 156, 18));
        btnCambiarEstadoIngrediente.setForeground(Color.WHITE);
        btnCambiarEstadoIngrediente.setBounds(20, 185, 250, 35);
        panel.add(btnCambiarEstadoIngrediente);

        btnEliminarIngredienteLocal = new JButton("Eliminar");
        btnEliminarIngredienteLocal.setBackground(new Color(231, 76, 60));
        btnEliminarIngredienteLocal.setForeground(Color.WHITE);
        btnEliminarIngredienteLocal.setBounds(20, 230, 250, 35);
        panel.add(btnEliminarIngredienteLocal);

        modeloIngredientesLocales = new DefaultTableModel(new String[]{"ID Ingrediente", "Nombre", "En Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaIngredientesLocales = new JTable(modeloIngredientesLocales);
        tablaIngredientesLocales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaIngredientesLocales);
        scroll.setBounds(300, 20, 550, 480);
        panel.add(scroll);

        return panel;
    }

    public Ingrediente ingredienteLocalSeleccionado() {
        return (Ingrediente) cbxIngredienteLocal.getSelectedItem();
    }

    public void cargarCombosIngredientesLocales(List<Ingrediente> list) {
        cbxIngredienteLocal.removeAllItems();
        for (Ingrediente i : list) {
            cbxIngredienteLocal.addItem(i);
        }
    }

    public void actualizarTablaIngredienteLocal (List<SucursalIngrediente> list) {
        modeloIngredientesLocales.setRowCount(0);
        for (SucursalIngrediente i : list) {
            modeloIngredientesLocales.addRow(new Object[]{
                i.getIngredienteId(),
                i.getNombreIngrediente(),
                i.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }

    public int getIdIngredienteLocalSeleccionado() {
        int fila = tablaIngredientesLocales.getSelectedRow();
        return (fila == -1) ? 0 : (int) tablaIngredientesLocales.getValueAt(fila, 0);
    }

    public JButton getBtnAgregarIngredienteLocal() {
        return btnAgregarIngredienteLocal;
    }

    public JButton getBtnCambiarEstadoIngrediente() {
        return btnCambiarEstadoIngrediente;
    }

    public JButton getBtnEliminarIngredienteLocal() {
        return btnEliminarIngredienteLocal;
    }

    // Método global de mensajes
    public void mostrarMensaje(String msg, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, msg, titulo, tipo);
    }
}
