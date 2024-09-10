package com.restaurante.app;

import com.restaurante.controller.ReservaController;
import com.restaurante.controller.MesaController;
import com.restaurante.model.Mesa;
import com.restaurante.model.Restaurante;
import com.restaurante.view.TelaPrincipal;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        try {
            // Cria uma instância de Restaurante
            Restaurante restaurante = new Restaurante("D'Itali");

            // Adiciona mesas ao restaurante
            restaurante.adicionarMesa(new Mesa(1, 4));
            restaurante.adicionarMesa(new Mesa(2, 2));
            restaurante.adicionarMesa(new Mesa(3, 6));
            restaurante.adicionarMesa(new Mesa(4, 10));
            restaurante.adicionarMesa(new Mesa(5, 4));
            restaurante.adicionarMesa(new Mesa(6, 4));

            // Carrega as reservas armazenadas em arquivo
            restaurante.carregarReservas();

            // Cria os controladores de Reserva e Mesa
            ReservaController reservaController = new ReservaController(restaurante);
            MesaController mesaController = new MesaController(restaurante);

            // Gerar estatísticas e salvar em um arquivo
            String caminhoArquivo = "estatisticas_reservas.txt"; // Caminho onde o arquivo será salvo
            reservaController.gerarEstatisticas(caminhoArquivo);

            // Inicializa a interface gráfica principal em uma thread da Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                TelaPrincipal telaPrincipal = new TelaPrincipal(reservaController, mesaController);
                telaPrincipal.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ocorreu um erro: " + e.getMessage());
        }
    }
}
