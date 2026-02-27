package org.practica1.main;

import org.practica1.controllers.LoginController;
import org.practica1.controllers.SucursalController;
import org.practica1.dao.SucursalDAO;
import org.practica1.dao.UsuarioDAO;
import org.practica1.views.LoginFrame;
import org.practica1.views.SuperAdminFrame;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // 1. Instanciar la Vista
            SuperAdminFrame superAdminFrame = new SuperAdminFrame();

            // 2. Instanciar el DAO (Modelo)
            SucursalDAO sucursalDAO = new SucursalDAO();

            // 3. Instanciar el Controlador pasándole ambos
            SucursalController controlador = new SucursalController(superAdminFrame, sucursalDAO);

            // 4. Mostrar la vista
            superAdminFrame.setVisible(true);
        });
    }
}