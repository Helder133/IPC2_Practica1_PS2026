package org.practica1.views;

import org.practica1.models.EnumPedido;
import org.practica1.models.Pedido;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class PanelPedidoCard extends JPanel {

    private Pedido pedido;
    private JLabel lblNumeroPedido;
    private JLabel lblEstado;
    private JLabel lblTiempoRestante;
    private JButton btnSiguienteEtapa;
    private JButton btnCancelar;
    private String nombresPizzas;

    private Font principal = new Font("Arial", Font.BOLD, 16);
    private Font secundario = new Font("Helvetica", Font.PLAIN, 12);

    public PanelPedidoCard(Pedido pedido, String nombresPizzas) {
        this.pedido = pedido;
        this.nombresPizzas = nombresPizzas;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(220, 230));
        this.setBackground(new Color(44, 62, 80));
        this.setBorder(new LineBorder(new Color(241, 196, 15), 2, true));

        lblNumeroPedido = new JLabel("Pedido #" + pedido.getPedidoId(), SwingConstants.CENTER);
        lblNumeroPedido.setFont(principal);
        lblNumeroPedido.setForeground(Color.WHITE);
        lblNumeroPedido.setBounds(10, 10, 200, 20);
        this.add(lblNumeroPedido);

        lblEstado = new JLabel("Estado: " + pedido.getEstado(), SwingConstants.CENTER);
        lblEstado.setFont(secundario);
        lblEstado.setForeground(new Color(189, 195, 199));
        lblEstado.setBounds(10, 35, 200, 20);
        this.add(lblEstado);

        JLabel lblDetalles = new JLabel(nombresPizzas, SwingConstants.CENTER);
        lblDetalles.setForeground(new Color(241, 196, 15)); // Amarillo para que resalte
        lblDetalles.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDetalles.setBounds(10, 60, 200, 45); // Espacio en el medio
        this.add(lblDetalles);

        lblTiempoRestante = new JLabel(pedido.getTiempoLimite() + " seg", SwingConstants.CENTER);
        lblTiempoRestante.setFont(secundario);
        lblTiempoRestante.setForeground(new Color(46, 204, 113)); // Verde por defecto
        lblTiempoRestante.setBounds(10, 110, 200, 30);
        this.add(lblTiempoRestante);

        btnSiguienteEtapa = new JButton("Siguiente Etapa");
        btnSiguienteEtapa.setBackground(new Color(52, 152, 219));
        btnSiguienteEtapa.setForeground(Color.WHITE);
        btnSiguienteEtapa.setFont(secundario);
        btnSiguienteEtapa.setFocusPainted(false);
        btnSiguienteEtapa.setBounds(10, 145, 200, 30);
        this.add(btnSiguienteEtapa);

        btnCancelar = new JButton("Cancelar Pedido");
        btnCancelar.setBackground(new Color(231, 76, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(secundario);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBounds(10, 185, 200, 30);
        this.add(btnCancelar);

        actualizarBotonesPorEstado();
    }

    public void actualizarBotonesPorEstado() {
        lblEstado.setText("Estado: " + pedido.getEstado());
        if (pedido.getEstado() == EnumPedido.Recibido || pedido.getEstado() == EnumPedido.Preparando) {
            btnCancelar.setEnabled(true);
        } else {
            btnCancelar.setEnabled(false);
            btnCancelar.setBackground(Color.GRAY);
        }
    }

    public Pedido getPedido() {
        return pedido;
    }

    public JLabel getLblTiempoRestante() {
        return lblTiempoRestante;
    }

    public JButton getBtnSiguienteEtapa() {
        return btnSiguienteEtapa;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

}
