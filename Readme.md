# 🍽️ Sistema de Reserva de Mesas

O projeto Sistema de Reserva de Mesas foi desenvolvido como parte de um curso de Programação Orientada a Objetos e tem como objetivo gerenciar reservas de mesas em um restaurante. Ele foi implementado em Java utilizando Swing para a interface gráfica e inclui funcionalidades para adicionar, listar e finalizar reservas, além de gerar relatórios e estatísticas.

## 📖 Índice

- [📝 Descrição do Projeto](#-descrição-do-projeto)
- [🏛️ Arquitetura](#️-arquitetura)
- [⚙️ Tecnologias Utilizadas](#️-tecnologias-utilizadas)
- [🚀 Execução e Utilização](#-execução-e-utilização)
    - [Pré-requisitos](#pré-requisitos)
    - [Passos de inicialização](#passos-de-inicialização)
    - [Passos para executar o projeto](#passos-para-executar-o-projeto)
- [🧱 Estrutura do Projeto](#-estrutura-do-projeto)
- [🚧 Desafios e Soluções](#-desafios-e-soluções)
- [👥 Contribuidores](#-contribuidores)

## 📝 Descrição do Projeto

O Sistema de Reserva de Mesas é uma aplicação desktop que permite a gestão de reservas de mesas em um restaurante. O sistema permite adicionar novas mesas, fazer reservas, listar mesas disponíveis e finalizar reservas. Além disso, o sistema é capaz de gerar relatórios com estatísticas sobre as reservas.

## 🏛️ Arquitetura

A arquitetura do sistema é dividida em várias camadas, com uma interface gráfica desenvolvida em Swing, e a lógica de negócio e controle implementadas em Java. O sistema é estruturado para garantir uma separação clara entre a interface do usuário e a lógica de controle.

![Arquitetura](./assets/arquitetura.png)

## ⚙️ Tecnologias Utilizadas

- **[Java](https://www.java.com/)** - Linguagem de programação utilizada para o desenvolvimento do sistema.
- **[Swing](https://docs.oracle.com/javase/tutorial/uiswing/)** - Biblioteca gráfica para a criação da interface do usuário.
- **[JDBC](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/)** - API para a comunicação com o banco de dados (caso aplicável).
- **[CSV](https://en.wikipedia.org/wiki/Comma-separated_values)** - Formato de arquivo utilizado para armazenar e ler dados de reservas.

## 🚀 Execução e Utilização

### Pré-requisitos

- **Java JDK 17 ou superior**
- **IDE de sua preferência (Eclipse, IntelliJ IDEA, NetBeans)**
- **Bibliotecas necessárias (caso existam)**
- **Arquivo CSV de reservas**

### Passos de inicialização

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/emanuelleGued/ReservaDeMesa.git
   cd ReservaDeMesa
   ```

2. **Abra o projeto na sua IDE:**

    - Importe o projeto como um projeto Java existente na sua IDE.

3. **Configure as dependências:**

    - Certifique-se de que todas as bibliotecas necessárias estão incluídas no classpath.

4. **Compile e execute o projeto:**

    - Compile o projeto e execute a classe principal `Main.java`.

### Passos para executar o projeto

1. **Adicionar Mesas:**

    - Use a interface gráfica para adicionar novas mesas ao restaurante.

2. **Fazer Reservas:**

    - Selecione uma mesa disponível e faça uma reserva preenchendo os detalhes necessários.

3. **Listar Mesas Disponíveis:**

    - Visualize todas as mesas disponíveis através da funcionalidade de listagem.

4. **Finalizar Reservas:**

    - Complete o processo de reserva preenchendo o horário de saída e finalizando a reserva.

5. **Gerar Estatísticas:**

    - Gere relatórios de estatísticas sobre as reservas feitas.

## 🧱 Estrutura do Projeto

```plaintext
.
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── restaurante/
│   │   │   │   │   ├── app/
│   │   │   │   │   │   ├── Main.java
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   ├── MesaController.java
│   │   │   │   │   │   ├── ReservaController.java
│   │   │   │   │   ├── model/
│   │   │   │   │   │   ├── exception/
│   │   │   │   │   │   │   ├── HoraInvalidaException.java 
│   │   │   │   │   │   │   ├── MesaIndisponivelException.java   
│   │   │   │   │   │   │   ├── ReservaNaoExistenteException.java
│   │   │   │   │   │   ├── Cliente.java
│   │   │   │   │   │   ├── Mesa.java     
│   │   │   │   │   │   ├── Reserva.java
│   │   │   │   │   │   ├── Restaurante.java
│   │   │   │   │   ├── view/
│   │   │   │   │   │   ├── TelaCancelamento.java
│   │   │   │   │   │   ├── TelaFinalizarReserva.java
│   │   │   │   │   │   ├── TelaListarReservas.java
│   │   │   │   │   │   ├── TelaPrincipal.java
│   │   │   │   │   │   ├── TelaReserva.java
│   ├── resources/
│   │   ├── reservas.csv
│   │   ├── estatisticas_reservas.txt

```

- **src/main/java/com/exemplo/controle/** - Contém as classes de controle do sistema.
- **src/main/java/com/exemplo/modelo/** - Contém as classes de modelo para reservas e mesas.
- **src/main/java/com/exemplo/visao/** - Contém as classes para a interface gráfica.
- **resources/** - Contém arquivos de recursos, como o CSV de reservas.
- **assets/** - Contém diagramas e outros recursos visuais.

## 🚧 Desafios e Soluções

### 1. Gerenciamento de Reservas Incompletas
- **Desafio:** Durante a implementação, houve dificuldades em garantir que todas as reservas fossem corretamente finalizadas e registradas.
- **Solução:** Implementação de verificações adicionais e validações para garantir que todas as reservas fossem processadas e armazenadas corretamente.

### 2. Integração com CSV
- **Desafio:** A leitura e escrita de arquivos CSV causavam erros devido a linhas mal formatadas.
- **Solução:** Adição de validações para verificar o formato das linhas antes da leitura e escrita, e implementação de mensagens de erro para linhas inválidas.

### 3. Interface Gráfica Intuitiva
- **Desafio:** Criar uma interface gráfica que fosse intuitiva e fácil de usar.
- **Solução:** Utilização do Java Swing para criar uma interface amigável e a realização de testes com usuários para ajustar a usabilidade.

## 👥 Contribuidores

- **[Emanuelle Garcia](https://github.com/emanuelleGued)**

