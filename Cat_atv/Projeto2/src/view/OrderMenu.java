package view;

import java.math.BigDecimal;
import java.util.Scanner;

import model.Orders;

public class OrderMenu {
    private Scanner scanner;

    public OrderMenu() {
        scanner = new Scanner(System.in);
    }

    // Método para ler o número do cliente para o pedido
    public int readCustomerNumber() {
        System.out.print("Informe o número do cliente para o pedido: ");
        return Integer.parseInt(scanner.nextLine());
    }

    // Método para ler o número do pedido
    public int readOrderNumber() {
        System.out.print("Informe o número do pedido: ");
        return Integer.parseInt(scanner.nextLine());
    }

    // Método para ler a descrição do pedido
    public String readOrderDescription() {
        System.out.print("Informe a descrição do pedido: ");
        return scanner.nextLine();
    }

    // Método para ler o preço do pedido
    public BigDecimal readOrderPrice() {
        System.out.print("Informe o preço do pedido: ");
        return new BigDecimal(scanner.nextLine());
    }

    // Método para ler o número do pedido a ser consultado
    public int readOrderNumberToSearch() {
        System.out.print("\nInforme o número do pedido a ser consultado: ");
        return Integer.parseInt(scanner.nextLine());
    }

    // Método para ler o número do pedido a ser excluído
    public int readOrderNumberToDelete() {
        System.out.print("Informe o número do pedido a ser excluído: ");
        return Integer.parseInt(scanner.nextLine());
    }

    
 // Método para exibir informações do pedido
    public void displayOrderInfo(Orders orders) {
        System.out.println("\nNúmero do Pedido: " + orders.getNumber());
        System.out.println("Descrição do Pedido: " + orders.getDescription());
        System.out.println("Preço do Pedido: " + orders.getPrice());
        System.out.println("ID do Cliente: " + orders.getCustomerId());
    }
    
    public void closeScanner() {
        scanner.close();
    }
}
