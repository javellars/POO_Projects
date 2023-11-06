package view;


import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public void displayWelcomeMessage() {
        System.out.println("Bem-vindo ao Sistema de Gerenciamento de Clientes e Pedidos");
        System.out.println("Versão: 5.0");
        System.out.println("Data da última atualização: 05/11/2023\n");
    }

    public void displayMainMenu() {
        System.out.println("\nMenu Principal:");
        System.out.println("1. Clientes");
        System.out.println("2. Pedidos");
        System.out.println("3. Relatórios");
        System.out.println("4. Informações");
        System.out.println("5. Sair");
        System.out.print("Selecione uma opção: ");
    }

    public void displayCustomersMenu() {
        System.out.println("\nMenu Clientes:");
        System.out.println("1. Inserir cliente");
        System.out.println("2. Consultar cliente pelo identificador");
        System.out.println("3. Consultar cliente pelo nome");
        System.out.println("4. Apagar cliente pelo identificador");
        System.out.println("5. Voltar ao Menu Principal");
        System.out.print("Selecione uma opção: ");
    }

    public void displayOrdersMenu() {
        System.out.println("\nMenu Pedidos:");
        System.out.println("1. Inserir pedido para um cliente");
        System.out.println("2. Consultar pedido pelo número");
        System.out.println("3. Apagar pedido pelo número");
        System.out.println("4. Voltar ao Menu Principal");
        System.out.print("Selecione uma opção: ");
    }

    public void displayReportsMenu() {
        System.out.println("\nMenu Relatórios:");
        System.out.println("1. Clientes ordenados por identificador");
        System.out.println("2. Clientes ordenados por nome");
        System.out.println("3. Pedidos ordenados por número");
        System.out.println("4. Pedidos dos clientes ordenados por nome");
        System.out.println("5. Voltar ao Menu Principal");
        System.out.print("Selecione uma opção: ");
    }

    public void displayInfoMenu() {
        System.out.println("\nMenu Informações:");
        System.out.println("1. Ajuda");
        System.out.println("2. Sobre");
        System.out.println("3. Voltar ao Menu Principal");
        System.out.print("Selecione uma opção: ");
    }

    public int readUserChoice() {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Opção inválida. Escolha uma opção válida.");
        }
        return choice;
    }

    public void displayGoodbyeMessage() {
        System.out.println("\nObrigado por usar o Sistema de Gerenciamento. Até logo!");
    }

    public void closeScanner() {
        scanner.close();
    }
}
