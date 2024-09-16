package com.restaurante.view;

import com.restaurante.controller.MesaController;
import com.restaurante.controller.ReservaController;
import com.restaurante.model.Reserva;
import com.restaurante.model.exception.HoraInvalidaException;
import com.restaurante.model.exception.ReservaNaoExistenteException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaFinalizarReserva extends JFrame {
    private JTextField campoNumeroMesa;
    private JTextField campoHoraFim;
    private JTextField campoIdReserva;
    private ReservaController reservaController;
    private MesaController mesaController;

    public TelaFinalizarReserva(ReservaController reservaController, MesaController mesaController) {
        this.reservaController = reservaController;
        this.mesaController = mesaController;

        setTitle("Finalizar Reserva");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(4, 2));

        add(new JLabel("Número da Mesa:"));
        campoNumeroMesa = new JTextField();
        add(campoNumeroMesa);

        add(new JLabel("ID da Reserva:"));
        campoIdReserva = new JTextField();
        add(campoIdReserva);

        add(new JLabel("Hora de Saída (HH:mm):"));
        campoHoraFim = new JTextField();
        add(campoHoraFim);

        JButton botaoFinalizar = new JButton("Finalizar");
        botaoFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarReserva();
            }
        });
        add(botaoFinalizar);
    }

    private void finalizarReserva() {
        String idReservaStr = campoIdReserva.getText();
        String horaFim = campoHoraFim.getText();

        try {
            // Converte o ID de reserva de String para int
            int idReserva = Integer.parseInt(idReservaStr);

            // Busca a reserva pelo ID
            Reserva reserva = reservaController.buscarReservaPorNumeroId(idReservaStr);

            if (reserva == null) {
                JOptionPane.showMessageDialog(this, "Reserva não encontrada para o ID " + idReserva, "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica se a reserva já foi finalizada
            if (reserva.getHoraSaida() != null) {
                throw new IllegalStateException("A reserva já foi finalizada.");
            }

            // Valida a hora de saída
            if (!validarHoraFim(reserva, horaFim)) {
                JOptionPane.showMessageDialog(this, "Hora de saída inválida! Deve estar no formato HH:mm e ser posterior à hora de chegada.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Chama o método do controller para finalizar a reserva
            reservaController.finalizarReservaMesa(idReserva, horaFim);
            JOptionPane.showMessageDialog(this, "Reserva finalizada com sucesso!");
            dispose(); // Fecha a tela de finalizar reserva
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID da reserva inválido! Deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ReservaNaoExistenteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (HoraInvalidaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }



    private boolean validarHoraFim(Reserva reserva, String horaFim) {
        if (horaFim == null || horaFim.isEmpty() || horaFim.equals("null")) {
            horaFim = "00:00";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date horaChegada = sdf.parse(reserva.getHora());
            Date horaSaida = sdf.parse(horaFim);

            return horaSaida.after(horaChegada);
        } catch (ParseException e) {
            return false;
        }
    }

}
