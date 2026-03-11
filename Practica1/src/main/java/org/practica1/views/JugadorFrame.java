package org.practica1.views;

import javax.swing.*;
import java.awt.*;

public class JugadorFrame extends JFrame {

    private JLabel lblJugadorNombre;
    private JLabel lblNivel;
    private JLabel lblPuntaje;
    private JButton btnIniciarPartida;
    private JButton btnTerminarTurno;
    private JLabel lblTiempoGeneral;

    private JMenuItem itemCerrarSesion;
    private JMenuItem itemVerHistorial;

    private Font titulo = new Font("Arial", Font.BOLD, 18);
    private Font principal = new Font("Helvetica", Font.BOLD, 14);
    private Font secundario = new Font("Helvetica", Font.PLAIN, 13);

    private JPanel panelContenedorPedidos;

    public JugadorFrame() {
        setTitle("Pizza Express Tycoon - Estación de Trabajo");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 30));

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelHeader = new JPanel(null);
        panelHeader.setBackground(new Color(20, 20, 20));
        panelHeader.setBounds(0, 0, 1100, 80);
        this.add(panelHeader);

        lblJugadorNombre = new JLabel("Cocinero: Esperando...");
        lblJugadorNombre.setFont(titulo);
        lblJugadorNombre.setForeground(new Color(241, 196, 15));
        lblJugadorNombre.setBounds(20, 25, 220, 30);
        panelHeader.add(lblJugadorNombre);

        lblTiempoGeneral = new JLabel("Turno: 02:30");
        lblTiempoGeneral.setFont(new Font("Arial", Font.BOLD, 22));
        lblTiempoGeneral.setForeground(new Color(231, 76, 60));
        lblTiempoGeneral.setBounds(250, 25, 150, 30);
        panelHeader.add(lblTiempoGeneral);

        lblNivel = new JLabel("Nivel: 1");
        lblNivel.setFont(titulo);
        lblNivel.setForeground(Color.WHITE);
        lblNivel.setBounds(420, 25, 100, 30);
        panelHeader.add(lblNivel);

        lblPuntaje = new JLabel("Puntos: 0");
        lblPuntaje.setFont(titulo);
        lblPuntaje.setForeground(Color.WHITE);
        lblPuntaje.setBounds(540, 25, 110, 30);
        panelHeader.add(lblPuntaje);

        btnIniciarPartida = new JButton("INICIAR PARTIDA");
        btnIniciarPartida.setBackground(new Color(46, 204, 113));
        btnIniciarPartida.setForeground(Color.WHITE);
        btnIniciarPartida.setFont(principal);
        btnIniciarPartida.setBounds(660, 20, 170, 40);
        panelHeader.add(btnIniciarPartida);

        btnTerminarTurno = new JButton("Terminar");
        btnTerminarTurno.setBackground(new Color(231, 76, 60));
        btnTerminarTurno.setForeground(Color.WHITE);
        btnTerminarTurno.setFont(secundario);
        btnTerminarTurno.setBounds(840, 20, 130, 40);
        btnTerminarTurno.setEnabled(false);
        panelHeader.add(btnTerminarTurno);

        panelContenedorPedidos = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelContenedorPedidos.setBackground(new Color(30, 30, 30));

        JScrollPane scroll = new JScrollPane(panelContenedorPedidos);
        scroll.setBounds(20, 100, 945, 540);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuSistema = new JMenu("Sistema");

        itemCerrarSesion = new JMenuItem("Cerrar sesión");
        itemCerrarSesion.setForeground(new Color(231, 76, 60));

        itemVerHistorial = new JMenuItem("Ver Historial de Partidas");

        menuSistema.add(itemVerHistorial);
        menuSistema.add(itemCerrarSesion);
        menuBar.add(menuSistema);

        this.setJMenuBar(menuBar);
        this.add(scroll);
    }

    public void agregarTarjetaPedido(PanelPedidoCard tarjeta) {
        panelContenedorPedidos.add(tarjeta);
        panelContenedorPedidos.revalidate();
        panelContenedorPedidos.repaint();
    }

    public void removerTarjetaPedido(PanelPedidoCard tarjeta) {
        panelContenedorPedidos.remove(tarjeta);
        panelContenedorPedidos.revalidate();
        panelContenedorPedidos.repaint();
    }

    public JLabel getLblJugadorNombre() {
        return lblJugadorNombre;
    }

    public JLabel getLblTiempoGeneral() {
        return lblTiempoGeneral;
    }

    public JLabel getLblNivel() {
        return lblNivel;
    }

    public JLabel getLblPuntaje() {
        return lblPuntaje;
    }

    public JButton getBtnIniciarPartida() {
        return btnIniciarPartida;
    }

    public JButton getBtnTerminarTurno() {
        return btnTerminarTurno;
    }

    public JPanel getPanelContenedorPedidos() {
        return panelContenedorPedidos;
    }

    public JMenuItem getItemCerrarSesion() {
        return itemCerrarSesion;
    }

    public JMenuItem getItemVerHistorial() {
        return itemVerHistorial;
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void mostrarMensajeFlotante(String mensaje) {
        JDialog toast = new JDialog(this, false);
        toast.setUndecorated(true);
        toast.setLayout(new BorderLayout());

        JLabel lblToast = new JLabel(mensaje, SwingConstants.CENTER);
        lblToast.setOpaque(true);
        lblToast.setBackground(new Color(46, 204, 113));
        lblToast.setForeground(Color.WHITE);
        lblToast.setFont(principal);
        lblToast.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        toast.add(lblToast);
        toast.pack();

        toast.setLocationRelativeTo(this);
        toast.setLocation(toast.getX(), toast.getY() + 200);

        toast.setVisible(true);

        Timer timerOcultar = new Timer(3000, e -> toast.dispose());
        timerOcultar.setRepeats(false);
        timerOcultar.start();
    }

    public void mostrarMensajeFlotante2(String mensaje) {
        JDialog toast = new JDialog(this, false);
        toast.setUndecorated(true);
        toast.setLayout(new BorderLayout());

        JLabel lblToast = new JLabel(mensaje, SwingConstants.CENTER);
        lblToast.setOpaque(true);
        lblToast.setBackground(new Color(187, 150, 18));
        lblToast.setForeground(Color.WHITE);
        lblToast.setFont(principal);
        lblToast.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        toast.add(lblToast);
        toast.pack();

        toast.setLocationRelativeTo(this);
        toast.setLocation(toast.getX(), toast.getY() + 200);

        toast.setVisible(true);

        Timer timerOcultar = new Timer(3000, e -> toast.dispose());
        timerOcultar.setRepeats(false);
        timerOcultar.start();
    }



}
