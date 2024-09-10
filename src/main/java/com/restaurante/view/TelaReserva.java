package com.restaurante.view;

import com.restaurante.controller.ReservaController;
import com.restaurante.model.Cliente;
import com.restaurante.model.exception.MesaIndisponivelException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class TelaReserva extends JFrame {
    private JTextField campoNomeCliente;
    private JTextField campoTelefoneCliente;
    private JTextField campoNumeroMesa;
    private JTextField campoData;
    private JTextField campoHora;
    private ReservaController reservaController;

    public TelaReserva(ReservaController reservaController) {
        this.reservaController = reservaController;

        setTitle("Reservar Mesa");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(7, 2));


        add(new JLabel("Nome do Cliente:"));
        campoNomeCliente = new JTextField();
        add(campoNomeCliente);

        add(new JLabel("Telefone do Cliente:"));
        campoTelefoneCliente = new JTextField();
        add(campoTelefoneCliente);

        add(new JLabel("Número da Mesa:"));
        campoNumeroMesa = new JTextField();
        add(campoNumeroMesa);

        add(new JLabel("Data (dd/MM/yyyy):"));
        campoData = new JTextField();
        add(campoData);

        add(new JLabel("Hora (HH:mm):"));
        campoHora = new JTextField();
        add(campoHora);


        JButton botaoReservar = new JButton("Reservar");
        botaoReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarReserva();
            }
        });
        add(botaoReservar);
    }

    private void realizarReserva() {
        if (!validarCampos()) {
            return;
        }

        String nomeCliente = campoNomeCliente.getText();
        String telefoneCliente = campoTelefoneCliente.getText();
        int numeroMesa;
        try {
            numeroMesa = Integer.parseInt(campoNumeroMesa.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número da Mesa inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String data = campoData.getText();
        String horachegada = campoHora.getText();

        Cliente cliente = new Cliente(nomeCliente, telefoneCliente);

        try {
            reservaController.fazerReservaMesa(cliente, numeroMesa, data, horachegada);
            JOptionPane.showMessageDialog(this, "Reserva realizada com sucesso!");
            dispose();
        } catch (MesaIndisponivelException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        String nome = campoNomeCliente.getText().trim();
        String telefone = campoTelefoneCliente.getText().trim();
        String data = campoData.getText().trim();
        String hora = campoHora.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo 'Nome do Cliente' deve ser preenchido corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (telefone.length() < 9) {
            JOptionPane.showMessageDialog(this, "O campo 'Telefone do Cliente' deve ter no mínimo 9 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!validarData(data)) {
            JOptionPane.showMessageDialog(this, "O campo 'Data' deve estar no formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!validarHora(hora)) {
            JOptionPane.showMessageDialog(this, "O campo 'Hora' deve estar no formato HH:mm.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Validação da data no formato dd/MM/yyyy
    private boolean validarData(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Validação da hora no formato HH:mm
    private boolean validarHora(String hora) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(false);
        try {
            sdf.parse(hora);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
