package org.practica1.controllers;

import org.practica1.dao.UsuarioDAO;
import org.practica1.models.Usuario;
import org.practica1.views.LoginFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Optional;

public class LoginController {
    private final LoginFrame vista;
    private final UsuarioDAO dao;

    public LoginController(LoginFrame vista, UsuarioDAO dao) {
        this.vista = vista;
        this.dao = dao;

        this.vista.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                intentarLogin();
            }
        });
    }

    private void intentarLogin() {
        String email = vista.getEmail();
        String password = vista.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            vista.mostrarMensaje("Por favor, llene todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario u = new Usuario(0, "", email, password, null);
            Optional<Usuario> usuarioOpt = dao.login(u);

            if (usuarioOpt.isPresent()) {
                Usuario user = usuarioOpt.get();
                vista.mostrarMensaje("Bienvenido " + user.getNombre(), "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);

            } else {
                vista.mostrarMensaje("Credenciales incorrectas o usuario inactivo.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            vista.mostrarMensaje("Error de conexión a la base de datos.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
