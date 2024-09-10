package com.restaurante.view;

import com.restaurante.controller.ReservaController;
import com.restaurante.model.Reserva;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCancelamento extends JFrame {
    private ReservaController reservaController;

    private JTextField campoNumeroMesa;
    private JTextField campoIdReserva;
    private JButton botaoCancelar;

    public TelaCancelamento(ReservaController reservaController) {
        this.reservaController = reservaController;

        setTitle("Cancelar Reserva");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Número da Mesa:"));
        campoNumeroMesa = new JTextField();
        add(campoNumeroMesa);

        add(new JLabel("ID da Reserva:"));
        campoIdReserva = new JTextField(); // Inicializa o campo para capturar o ID da reserva
        add(campoIdReserva);


        botaoCancelar = new JButton("Cancelar Reserva");
        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarReserva();
            }
        });
        add(botaoCancelar);
    }

    private void cancelarReserva() {
        String idReserva = campoIdReserva.getText(); // Captura o texto do campo de ID da reserva

        if (idReserva == null || idReserva.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID da Reserva inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Busca a reserva usando o ID fornecido
            Reserva reserva = reservaController.buscarReservaPorNumeroId(idReserva);
            if (reserva != null) {
                // Cancela a reserva chamando o método no controller
                reservaController.cancelarReserva(reserva);
                JOptionPane.showMessageDialog(this, "Reserva cancelada com sucesso!");
                dispose(); // Fecha a tela de cancelamento
            } else {
                JOptionPane.showMessageDialog(this, "Reserva não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cancelar a reserva: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
