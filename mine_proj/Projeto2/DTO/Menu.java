package dataBaseReference.DTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import dataBaseReference.System.Controller;
import dataBaseReference.System.DataBaseType;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private Controller cliente = new Controller(DataBaseType.INVALID);
    private Controller pedido = new Controller(DataBaseType.INVALID);
    int escolhaDataBase;

    public Menu(int escolhaDataBase) {
        if (escolhaDataBase == 1) {
            pedido = new Controller(DataBaseType.MARIADB);
            cliente = new Controller(DataBaseType.MARIADB);
            pedido.start();
            cliente.start();
        } else if (escolhaDataBase == 2) {
            pedido = new Controller(DataBaseType.MEMORY);
            cliente = new Controller(DataBaseType.MEMORY);
            pedido.start();
            cliente.start();
        }
    }

    public void mensagemBoasVindas() {
        System.out.println("#################################################");
        System.out.println("Seja bem-vindo ao Sistema de Gerenciamento");
        System.out.println("--------------------------------------------------");
        System.out.println(
                "Este sistema tem a finalidade de oferecer funcionalidades essenciais para a administração de informações de clientes e pedidos. Os usuários poderão inserir, consultar, atualizar e apagar registros de clientes e pedidos, bem como gerar relatórios e acessar informações relevantes. Além disso, o sistema deve garantir a segurança ao limitar o uso de identificadores específicos para cada grupo de usuários, evitando conflitos e mantendo a integridade dos dados no servidor MariaDB. Através de uma estrutura bem organizada, o programa oferecerá uma experiência de usuário intuitiva, permitindo uma interação eficiente com o banco de dados, contribuindo assim para a gestão eficaz de informações e facilitando a tomada de decisões.");
        System.out.println("--------------------------------------------------");
        System.out.println("Versão: 1.0");
        System.out.println("Última atualização: " + new Date());
        System.out.println("#################################################");
        System.out.println();
    }

    public void menuPrincipal() {
        System.out.println("Menu Principal:");
        System.out.println("1. Clientes");
        System.out.println("2. Pedidos");
        System.out.println("3. Relatórios");
        System.out.println("4. Informações");
        System.out.println("5. Sair");
        System.out.print("Selecione uma opção para prosseguir: ");
    }

    // Clientes

    public void menuClientes() {
        System.out.println("#########################################");
        System.out.println("Clientes:");
        System.out.println("1. Inserir clientes");
        System.out.println("2. Consultar cliente pelo identificador");
        System.out.println("3. Consultar cliente pelo nome");
        System.out.println("4. Apagar cliente pelo identificador");
        System.out.println("5. Voltar ao Menu Principal");
        System.out.print("Selecione uma opção para prosseguir: ");

        int opcaoMenu = scanner.nextInt();

        switch (opcaoMenu) {
            case 1:
                // 1. Inserir clientes
                System.out.print("Digite as informações do cliente: ");
                System.out.print("ID: ");
                int id = scanner.nextInt();
                System.out.print("nome: ");
                String nome = scanner.next();
                System.out.print("cidade: ");
                String cidade = scanner.next();
                System.out.print("estado: ");
                String estado = scanner.next();

                Customer newCustomer = new Customer(id, nome, cidade, estado);
                cliente.inserirCliente(id, newCustomer);
                System.out.println("Cliente inserido");
                break;

            case 2:
                // 2. Consultar cliente pelo identificador
                System.out.print("Digite o identificador(ID) do cliente a ser consultado: ");
                int idc = scanner.nextInt();
                Customer consultado = cliente.consultarClientePorId(idc);

                if (consultado != null) {
                    System.out.println("Cliente encontrado: " + consultado.toString());
                } else {
                    System.out.println("Cliente com esse ID não foi encontrado.");
                }
                break;

            case 3:
                // 3. Consultar cliente pelo nome
                System.out.print("Digite o nome do cliente a ser consultado: ");
                String nomeConsulta = scanner.next();
                Customer clientePorNome = cliente.consultarClientePorNome(nomeConsulta);

                if (clientePorNome != null) {
                    System.out.println("Cliente encontrado: " + clientePorNome.toString());
                } else {
                    System.out.println("Cliente com esse nome não foi encontrado.");
                }
                break;

            case 4:
                // 4. Apagar cliente pelo identificador
                System.out.print("Digite o ID do cliente a ser apagado: ");
                int idApagar = scanner.nextInt();
                Customer clienteApagado = cliente.apagarCliente(idApagar);

                if (clienteApagado != null) {
                    System.out.println("Cliente apagado: " + clienteApagado.toString());
                } else {
                    System.out.println("Cliente com esse ID não foi encontrado");
                }
                break;

            case 5:
                return;

            default:
                System.out.println("Selecione uma opção válida do menu utilizando números ");
        }
    }

    // Menu Pedidos
    public void MenuPedidos() {
        System.out.println("========================================");
        System.out.println("Pedidos:");
        System.out.println("1. Inserir pedido para um cliente");
        System.out.println("2. Consultar pedido pelo número");
        System.out.println("3. Apagar pedido pelo número");
        System.out.println("4. Voltar ao Menu Principal");
        int opcao;

        while (true) {
            try {
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Por favor, insira um número inteiro.");
                scanner.next();
            }
        }

        switch (opcao) {
            case 1:
                System.out.print("Digite o número do pedido: ");
                int numeroPedido = scanner.nextInt();
                System.out.print("Digite o ID do cliente: ");
                int idCliente = scanner.nextInt();
                System.out.print("Digite a descrição do pedido: ");
                String descricao = scanner.next();
                System.out.print("Digite o preço do pedido: ");
                BigDecimal preco = scanner.nextBigDecimal();

                pedido.inserirPedido(numeroPedido, idCliente, descricao, preco);
                break;

            case 2:
                System.out.print("Digite o número do pedido que você deseja consultar: ");
                int numeroConsulta = scanner.nextInt();
                pedido.consultarPedidoPorNumero(numeroConsulta);
                break;

            case 3:
                System.out.print("Digite o número do pedido que você deseja apagar: ");
                int numeroApagar = scanner.nextInt();
                pedido.excluirPedido(numeroApagar);
                break;

            case 4:
                return;

            default:
                System.out.println("Opção inválida. Selecione uma opção válida.");
        }
    }

    // Menu Relatórios

    public void menuRelatorios() {
        System.out.println("#########################################");
        System.out.println("Relatórios:");
        System.out.println("1. Clientes ordenados por identificador");
        System.out.println("2. Clientes ordenados por nome");
        System.out.println("3. Pedidos ordenados por número");
        System.out.println("4. Pedidos dos clientes ordenados por nome");
        System.out.println("5. Voltar ao Menu Principal");
        System.out.println("Escolha uma opção: ");
    }

    public void menuInformacoes() {
        System.out.println("#########################################");
        System.out.println("Informações:");
        System.out.println("1. Ajuda");
        System.out.println("2. Sobre");
        System.out.println("3. Voltar ao Menu Principal");
        System.out.println("Escolha uma opção: ");

        int opcaoMenu = scanner.nextInt();

        switch (opcaoMenu) {
            case 1:
                // Ajuda
                System.out.println("--------------------------------------------------");
                System.out.println("Sistema de Gerenciamento");
                System.out.println("--------------------------------------------------");
                System.out.println(
                        "Este sistema tem a finalidade de oferecer funcionalidades essenciais para a administração de informações de clientes e pedidos. Os usuários poderão inserir, consultar, atualizar e apagar registros de clientes e pedidos, bem como gerar relatórios e acessar informações relevantes. Além disso, o sistema deve garantir a segurança ao limitar o uso de identificadores específicos para cada grupo de usuários, evitando conflitos e mantendo a integridade dos dados no servidor MariaDB. Através de uma estrutura bem organizada, o programa oferecerá uma experiência de usuário intuitiva, permitindo uma interação eficiente com o banco de dados, contribuindo assim para a gestão eficaz de informações e facilitando a tomada de decisões.");
                System.out.println("--------------------------------------------------");
                System.out.println("Funcionalidades:");
                System.out.println("--------------------------------------------------");
                System.out.println("1. Inserir, consultar (pelo id ou nome) e apagar clientes");
                System.out.println("2. Inserir, consultar (pelo número) e apagar pedidos");
                System.out.println("3. Gerar relatórios ordenados");
                System.out.println("--------------------------------------------------");
                System.out.println("Guia de Utilização");
                System.out.println("--------------------------------------------------");
                System.out.println(
                        "Para ter acesso ao menu, é necessário digitar o número correspondente à opção a ser realizada. Por exemplo, considerando esse menu: ");
                System.out.println("1. Criar um pedido para um cliente");
                System.out.println("2. Pesquisar um pedido por número");
                System.out.println("3. Eliminar um pedido por número");
                System.out.println("4. Regressar ao Menu Principal");
                System.out.println("Se quiser criar um pedido, deve ser digitado o valor '1'.");
                System.out.println("--------------------------------------------------");
                break;

            case 2:
                // Sobre
                System.out.println("--------------------------------------------------");
                System.out.println("Versão: 1.0");
                System.out.println("--------------------------------------------------");
                System.out.println("O trabalho foi desenvolvido pelo grupo B.3 da Disciplina SI400 ");

                System.out.println("--------------------------------------------------");
                System.out.println("Grupo:");
                System.out.println("Laís Azevedo Soares 173455");
                System.out.println("Jullia Souza De Avelar 214539");
                System.out.println("Clara Luiza De Andrade Klippel 234458");
                System.out.println("Victor Ferreira Da Silva 177950");
                System.out.println("Felipe Lemos Ferreira 174483");
                System.out.println("--------------------------------------------------");
                break;
        }
    }

    public void menus() {
        mensagemBoasVindas();

        while (true) {
            menuPrincipal();
            int opcaoMenu = scanner.nextInt();

            switch (opcaoMenu) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuPedidos();
                    break;
                case 3:
                    menuRelatorios();
                    break;
                case 4:
                    menuInformacoes();
                    break;
                case 5:
                    System.out.println("Sistema encerrado");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Selecione uma opção válida.");
            }
        }
    }

    public void executarMenus() {
        mensagemBoasVindas();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            menuPrincipal();
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    MenuPedidos();
                    break;
                case 3:
                    menuRelatorios();
                    break;
                case 4:
                    menuInformacoes();
                    break;
                case 5:
                    System.out.println(
                            "O programa foi encerrado. Obrigado por usar o Sistema de Gerenciamento de Clientes e Pedidos!");
                    scanner.close();
                    cliente.stop();
                    pedido.stop();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
            }
        }
    }
}