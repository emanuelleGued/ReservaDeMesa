package com.restaurante.view;

import com.restaurante.controller.ReservaController;
import com.restaurante.model.Reserva;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListarReservas extends JFrame {
    private ReservaController reservaController;
    private JTable tabelaReservas;

    public TelaListarReservas(ReservaController reservaController) {
        this.reservaController = reservaController;

        setTitle("Listar Reservas");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Adiciona a coluna "ID da Reserva"
        String[] colunas = {"ID da Reserva", "Nome do Cliente", "Telefone", "Número da Mesa", "Data", "Hora"};
        Object[][] dados = obterDadosReservas();
        tabelaReservas = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaReservas);
        add(scrollPane, BorderLayout.CENTER);
    }

    private Object[][] obterDadosReservas() {
        List<Reserva> reservas = reservaController.listarReservas();
        Object[][] dados = new Object[reservas.size()][6]; // Atualizado para 6 colunas
        for (int i = 0; i < reservas.size(); i++) {
            Reserva reserva = reservas.get(i);
            dados[i][0] = reserva.getId(); // Exibe o ID da reserva
            dados[i][1] = reserva.getCliente().getNome();
            dados[i][2] = reserva.getCliente().getTelefone();
            dados[i][3] = reserva.getMesa().getNumero();
            dados[i][4] = reserva.getData();
            dados[i][5] = reserva.getHora();
        }
        return dados;
    }
}
