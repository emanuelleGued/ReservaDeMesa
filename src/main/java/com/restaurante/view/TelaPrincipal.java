package com.restaurante.view;

import com.restaurante.controller.ReservaController;
import com.restaurante.controller.MesaController;
import com.restaurante.model.Mesa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaPrincipal extends JFrame {
    private ReservaController reservaController;
    private MesaController mesaController;
    private JTable tabelaMesas;
    private JButton botaoReservar;
    private JButton botaoCancelar;
    private JButton botaoListarReservas;
    private JButton botaoFinalizarReserva;

    public TelaPrincipal(ReservaController reservaController, MesaController mesaController) {
        if (reservaController == null || mesaController == null) {
            throw new IllegalArgumentException("Controle de reservas ou mesas não pode ser nulo");
        }
        this.reservaController = reservaController;
        this.mesaController = mesaController;

        setTitle("Sistema de Reserva de Mesas");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        String[] colunas = {"Número da Mesa", "Capacidade", "Disponível"};
        Object[][] dados = obterDadosMesas();
        tabelaMesas = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaMesas);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        botaoReservar = new JButton("Reservar Mesa");
        botaoCancelar = new JButton("Cancelar Reserva");
        botaoListarReservas = new JButton("Listar Reservas");
        botaoFinalizarReserva = new JButton("Finalizar Reserva");

        painelBotoes.add(botaoReservar);
        painelBotoes.add(botaoCancelar);
        painelBotoes.add(botaoListarReservas);
        painelBotoes.add(botaoFinalizarReserva);

        add(painelBotoes, BorderLayout.SOUTH);

        botaoReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaReserva telaReserva = new TelaReserva(reservaController);
                telaReserva.setVisible(true);
                atualizarTabela();
            }
        });

        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCancelamento telaCancelamento = new TelaCancelamento(reservaController);
                telaCancelamento.setVisible(true);
                atualizarTabela();
            }
        });

        botaoListarReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaListarReservas telaListarReservas = new TelaListarReservas(reservaController);
                telaListarReservas.setVisible(true);
            }
        });

        botaoFinalizarReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaFinalizarReserva telaFinalizarReserva = new TelaFinalizarReserva(reservaController, mesaController);
                telaFinalizarReserva.setVisible(true);
            }
        });
    }

    private Object[][] obterDadosMesas() {
        List<Mesa> mesas = mesaController.listarMesas();
        if (mesas == null) {
            mesas = List.of(); // Retorna uma lista vazia se mesas for nulo
        }
        Object[][] dados = new Object[mesas.size()][3];
        for (int i = 0; i < mesas.size(); i++) {
            Mesa mesa = mesas.get(i);
            dados[i][0] = mesa.getNumero();
            dados[i][1] = mesa.getCapacidade();
            dados[i][2] = mesa.isDisponivel() ? "Sim" : "Não";
        }
        return dados;
    }

    private void atualizarTabela() {
        Object[][] novosDados = obterDadosMesas();
        tabelaMesas.setModel(new javax.swing.table.DefaultTableModel(
                novosDados,
                new String[]{"Número da Mesa", "Capacidade", "Disponível"}
        ));
    }
}
