package org.practica1.controllers;

import org.apache.commons.lang3.StringUtils;
import org.practica1.dao.*;
import org.practica1.models.EnumUsuario;
import org.practica1.models.Usuario;
import org.practica1.views.AdminTiendaFrame;
import org.practica1.views.JugadorFrame;
import org.practica1.views.LoginFrame;
import org.practica1.views.SuperAdminFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Optional;

public class LoginController implements ActionListener {
    private final LoginFrame vista;
    private final UsuarioDAO dao;

    public LoginController(LoginFrame vista, UsuarioDAO dao) {
        this.vista = vista;
        this.dao = dao;

        this.vista.getBtnIngresar().addActionListener(this);

    }

    public void actionPerformed(ActionEvent actionEvent) {
        intentarLogin();
    }

    private void intentarLogin() {
        String email = vista.getEmail();
        String password = vista.getPassword();

        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            vista.mostrarMensaje("Por favor, llene todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario u = new Usuario(0, "", email, password, null);
            Optional<Usuario> usuarioOpt = dao.login(u);

            if (usuarioOpt.isPresent()) {
                Usuario user = usuarioOpt.get();
                vista.mostrarMensaje("Bienvenido " + user.getNombre(), "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
                //Super Admin
                if (user.getRol().equals(EnumUsuario.SUPER_ADMIN)) {
                    SuperAdminFrame superAdminFrame = getSuperAdminFrame();
                    superAdminFrame.setVisible(true);
                    vista.dispose();
                    //Admin Tienda
                } else if (user.getRol().equals(EnumUsuario.ADMIN_TIENDA)) {
                    AdminTiendaFrame adminTiendaFrame = getAdminTiendaFrame(user.getSucursal_id());
                    adminTiendaFrame.setVisible(true);
                    vista.dispose();
                } else {
                    JugadorFrame jugadorFrame = getJugadorFrame(user);
                    jugadorFrame.setVisible(true);
                    vista.dispose();
                }

            } else {
                vista.mostrarMensaje("Credenciales incorrectas o usuario inactivo.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            vista.mostrarMensaje("Error de conexión a la base de datos.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JugadorFrame getJugadorFrame(Usuario user) {
        JugadorFrame jugadorFrame = new JugadorFrame();

        JugadorController jugadorController = new JugadorController(jugadorFrame, user, new PartidaDAO(), new ConfiguracionDAO(), new PedidoDAO(), new DetallePedidoDAO(), new HistorialPedidoDAO(), new SucursalProductoDAO(), new ProductoIngredienteDAO(), new SucursalIngredienteDAO());
        CerrarSesionController cerrarSesionController = new CerrarSesionController(jugadorFrame);
        return jugadorFrame;
    }

    private static SuperAdminFrame getSuperAdminFrame() {
        SuperAdminFrame superAdminFrame = new SuperAdminFrame();

        SucursalDAO sucursalDAO = new SucursalDAO();
        UsuarioDAO usuarioDao = new UsuarioDAO();
        ConfiguracionDAO configuracionDAO = new ConfiguracionDAO();
        ReportesDAO reportesDAO = new ReportesDAO();

        SucursalController sucursalController = new SucursalController(superAdminFrame, sucursalDAO);
        UsuarioController usuarioController = new UsuarioController(superAdminFrame, usuarioDao, sucursalDAO);
        ConfiguracionController configuracionController = new ConfiguracionController(superAdminFrame, configuracionDAO);
        ReportesController reportesController = new ReportesController(superAdminFrame, reportesDAO);

        CerrarSesionController cerrarSesionController = new CerrarSesionController(superAdminFrame);
        return superAdminFrame;
    }

    private static AdminTiendaFrame getAdminTiendaFrame(int sucursalId) {
        AdminTiendaFrame adminTiendaFrame = new AdminTiendaFrame();

        IngredienteDAO ingredienteDAO = new IngredienteDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        ProductoIngredienteDAO productoIngredienteDAO = new ProductoIngredienteDAO();
        SucursalIngredienteDAO sucursalIngredienteDAO = new SucursalIngredienteDAO();
        SucursalProductoDAO sucursalProductoDAO = new SucursalProductoDAO();
        ReportesDAO reportesDAO = new ReportesDAO();

        IngredienteController ingredienteController = new IngredienteController(adminTiendaFrame, ingredienteDAO);
        ProductoController productoController = new ProductoController(adminTiendaFrame, productoDAO, new ConfiguracionDAO());
        ProductoIngredienteController productoIngredienteController = new ProductoIngredienteController(adminTiendaFrame, productoIngredienteDAO, productoDAO, ingredienteDAO);
        SucursalIngredienteController sucursalIngredienteController = new SucursalIngredienteController(sucursalId, adminTiendaFrame, sucursalIngredienteDAO, ingredienteDAO);
        SucursalProductoController sucursalProductoController = new SucursalProductoController(sucursalId, adminTiendaFrame, sucursalProductoDAO, productoDAO);
        ReportesController reportesController = new ReportesController(adminTiendaFrame, reportesDAO, sucursalId);

        CerrarSesionController cerrarSesionController = new CerrarSesionController(adminTiendaFrame);
        return adminTiendaFrame;
    }
}
