package org.practica1.controllers;

import org.apache.commons.lang3.StringUtils;
import org.practica1.dao.ConfiguracionDAO;
import org.practica1.dao.SucursalDAO;
import org.practica1.dao.UsuarioDAO;
import org.practica1.models.EnumUsuario;
import org.practica1.models.Usuario;
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

                if (user.getRol().equals(EnumUsuario.SUPER_ADMIN)) {
                    SuperAdminFrame superAdminFrame = new SuperAdminFrame();

                    SucursalDAO sucursalDAO = new SucursalDAO();
                    UsuarioDAO usuarioDao = new UsuarioDAO();
                    ConfiguracionDAO configuracionDAO = new ConfiguracionDAO();

                    SucursalController sucursalController = new SucursalController(superAdminFrame,sucursalDAO);
                    UsuarioController usuarioController = new UsuarioController(superAdminFrame,usuarioDao,sucursalDAO);
                    ConfiguracionController configuracionController = new ConfiguracionController(superAdminFrame,configuracionDAO);

                    superAdminFrame.setVisible(true);
                    vista.dispose();
                } else if (user.getRol().equals(EnumUsuario.ADMIN_TIENDA)) {
                    vista.mostrarMensaje("Pantalla de Admin de Tienda en construcción...", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    vista.mostrarMensaje("Pantalla de Jugador en construcción...", "Info", JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                vista.mostrarMensaje("Credenciales incorrectas o usuario inactivo.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            vista.mostrarMensaje("Error de conexión a la base de datos.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
