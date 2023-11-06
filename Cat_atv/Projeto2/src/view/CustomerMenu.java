package view;

import java.util.Scanner;

import model.Customer;

public class CustomerMenu {
    private Scanner scanner;

    public CustomerMenu() {
        scanner = new Scanner(System.in);
    }
    
    //INSERIR CLIENTE
    
    // Método para ler o ID do cliente
    public int readCustomerId() {
        System.out.print("\nInforme o ID do cliente: ");
        return Integer.parseInt(scanner.nextLine());
    }

    // Método para ler o nome do cliente
    public String readCustomerName() {
        System.out.print("Informe o nome do cliente: ");
        return scanner.nextLine();
    }

    // Método para ler a cidade do cliente
    public String readCustomerCity() {
        System.out.print("Informe a cidade de residência do cliente: ");
        return scanner.nextLine();
    }

    // Método para ler o estado do cliente
    public String readCustomerState() {
        System.out.print("Informe o estado de residência do cliente: ");
        return scanner.nextLine();
    }
    
    
    //CONSULTAR CLIENTE

    // Método para ler o ID do cliente a ser consultado
    public int readCustomerIdToSearch() {
        System.out.print("\nInforme o ID do cliente a ser consultado: ");
        return Integer.parseInt(scanner.nextLine());
    }

    // Método para exibir informações do cliente
    public void displayCustomerInfo(Customer customer) {
        System.out.println("ID: " + customer.getId());
        System.out.println("Nome: " + customer.getName());
        System.out.println("Cidade: " + customer.getCity());
        System.out.println("Estado: " + customer.getState());
    }

}

