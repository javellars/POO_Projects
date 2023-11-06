package dataBaseReference.System;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import dataBaseReference.DAO.AbstractCustomerDAO;
import dataBaseReference.DAO.AbstractOrderDAO;
import dataBaseReference.DAO.Customer_DB_DAO;
import dataBaseReference.DAO.Customer_Mem_DAO;
import dataBaseReference.DAO.Order_DB_DAO;
import dataBaseReference.DAO.Order_Mem_DAO;
import dataBaseReference.DTO.Customer;
import dataBaseReference.DTO.Orders;
import dataBaseReference.RDBMS.MariaDBConnection;
import dataBaseReference.RDBMS.MemoryDBConnection;

public class Controller {
   private AbstractCustomerDAO customerDAO = null;
   private AbstractOrderDAO ordersDAO = null;
   private MariaDBConnection myDBConnection = null;
   private MemoryDBConnection memoryDBConnection = null;
   private DataBaseType selectedDataBase = DataBaseType.INVALID;

   public Controller(DataBaseType selectedDataBase) {
      super();
      this.selectedDataBase = selectedDataBase;
   }

   private void openConnection() {
      switch (selectedDataBase) {
         case MEMORY: {
            memoryDBConnection = new MemoryDBConnection();
            this.customerDAO = new Customer_Mem_DAO(memoryDBConnection);
            this.ordersDAO = new Order_Mem_DAO(memoryDBConnection);
         }
            break;
         case MARIADB: {
            myDBConnection = new MariaDBConnection();
            this.customerDAO = new Customer_DB_DAO(myDBConnection.getConnection());
            this.ordersDAO = new Order_DB_DAO(myDBConnection.getConnection());
         }
            break;
         default: {
            System.out.println("Database selection not supported.");
            throw new InvalidParameterException("Selector is unspecified: " + selectedDataBase);
         }
      }
   }

   private void closeConnection() {
      if (myDBConnection != null) {
         myDBConnection.close();
      }
      if (memoryDBConnection != null) {
         memoryDBConnection.close();
      }
   }

   public Controller start() {
      openConnection();
      return null;
   }

   public Controller stop() {
      closeConnection();
      return null;
   }

   public Controller test() {
      openConnection();
      insertData();
      requestData();
      updateData();
      deleteData();
      deleteAllData();
      requestData();
      closeConnection();
      return null;
   }

   private void insertData() {
      System.out.println("Create 4 random customers");
      try {
         for (int i = 1; i <= 4; i++) {
            Customer customer = new Customer();
            customer.setId(i);
            customer.setName("Customer " + i);
            customer.setCity("City " + i);
            customer.setState("State " + i);
            customerDAO.addCustomer(customer);

            // Create 2 random orders for each customer
            for (int j = 1; j <= 2; j++) {
               Orders order = new Orders();
               order.setNumber((i - 1) * 2 + j); // Ensure unique order numbers
               order.setCustomerId(i);
               order.setDescription("Order " + j + " for Customer " + i);
               order.setPrice(new BigDecimal(new Random().nextDouble() * 100.0)); // Random
               ordersDAO.addOrder(order);
            }
         }
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      System.out.println("Random customers and orders created successfully!");
   }

   private void requestData() {

      System.out.println("Requesting all customers");
      try {
         List<Customer> customers = customerDAO.getAllCustomersOrderedByName();
         for (Customer customer : customers) {
            System.out.println(customer.getName());
         }
      } catch (SQLException e) {
         System.err.println("Error retrieving customers: " + e.getMessage());
      }

      System.out.println("Requesting single customer");
      try {
         int customerId = 1; // Replace with the desired customer ID
         Customer customer = customerDAO.getCustomerById(customerId);

         if (customer != null) {
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("City: " + customer.getCity());
            System.out.println("State: " + customer.getState());
         } else {
            System.out.println("Customer not found.");
         }
      } catch (SQLException e) {
         System.err.println("Error retrieving customer: " + e.getMessage());
      }

      System.out.println("Requesting all orders from a customer");
      try {
         int customerId = 1; // Replace with the desired customer
                             // ID
         List<Orders> customerOrders = ordersDAO.getOrdersByCustomerId(customerId);

         for (Orders order : customerOrders) {
            System.out.println("Order Number: " + order.getNumber());
            System.out.println("Description: " + order.getDescription());
            System.out.println("Price: " + order.getPrice());
            System.out.println();
         }
      } catch (SQLException e) {
         System.err.println("Error retrieving customer orders: " + e.getMessage());
      }

      System.out.println("Requesting a single order");
      try {
         int orderNumber = 1; // Replace with the desired order number
         Orders order = ordersDAO.getOrderByNumber(orderNumber);

         if (order != null) {
            System.out.println("Order Number: " + order.getNumber());
            System.out.println("Customer ID: " + order.getCustomerId());
            System.out.println("Description: " + order.getDescription());
            System.out.println("Price: " + order.getPrice());
         } else {
            System.out.println("Order not found.");
         }
      } catch (SQLException e) {
         System.err.println("Error retrieving order: " + e.getMessage());
      }
   }

   private void updateData() {
      // Single Customer
      try {
         int customerId = 1; // Replace with the desired customer ID
         Customer customer = customerDAO.getCustomerById(customerId);

         if (customer != null) {
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("City: " + customer.getCity());
            System.out.println("State: " + customer.getState());

            customer.setCity("Limeira");
            customer.setState("SP");
            customerDAO.updateCustomer(customer);
         } else {
            System.out.println("Customer not found.");
         }
      } catch (SQLException e) {
         System.err.println("Error retrieving customer: " + e.getMessage());
      }

   }

   private void deleteData() {
      try {
         int orderNumber = 1; // Replace with the desired order number
         Orders order = ordersDAO.getOrderByNumber(orderNumber);

         if (order != null) {
            System.out.println("Order Number: " + order.getNumber());
            System.out.println("Customer ID: " + order.getCustomerId());
            System.out.println("Description: " + order.getDescription());
            System.out.println("Price: " + order.getPrice());

            ordersDAO.deleteOrder(order.getNumber());
         } else {
            System.out.println("Order not found.");
         }
      } catch (SQLException e) {
         System.err.println("Error retrieving order: " + e.getMessage());
      }
   }

   private void deleteAllData() {
      System.out.println("Deleting all data");

      try {
         ordersDAO.deleteAllOrders();
         customerDAO.deleteAllCustomers();
      } catch (SQLException e) {
         System.err.println("Error retrieving order: " + e.getMessage());
      }
   }

   public void inserirCliente(int id, String nome, String cidade, String estado) {
      Customer cliente = new Customer();
      cliente.setId(id);
      cliente.setName(nome);
      cliente.setCity(cidade);
      cliente.setState(estado);

      try {
         customerDAO.addCustomer(cliente);
         System.out.println("Cliente inserido com sucesso!");
      } catch (SQLException e) {
         System.err.println("Erro ao inserir cliente: " + e.getMessage());
      }
   }

   public void inserirPedido(int numero, int idCliente, String descricao, BigDecimal preco) {
      Orders pedido = new Orders();
      pedido.setNumber(numero);
      pedido.setCustomerId(idCliente);
      pedido.setDescription(descricao);
      pedido.setPrice(preco);

      try {
         ordersDAO.addOrder(pedido);
         System.out.println("Pedido inserido com sucesso!");
      } catch (SQLException e) {
         System.err.println("Erro ao inserir pedido: " + e.getMessage());
      }
   }

   public void atualizarCliente(int id, String nome, String cidade, String estado) {
      Customer cliente = new Customer();
      cliente.setId(id);
      cliente.setName(nome);
      cliente.setCity(cidade);
      cliente.setState(estado);

      try {
         customerDAO.updateCustomer(cliente);
         System.out.println("Cliente atualizado com sucesso!");
      } catch (SQLException e) {
         System.err.println("Erro ao atualizar cliente: " + e.getMessage());
      }
   }

   public void atualizarPedido(int numero, int idCliente, String descricao, BigDecimal preco) {
      Orders pedido = new Orders();
      pedido.setNumber(numero);
      pedido.setCustomerId(idCliente);
      pedido.setDescription(descricao);
      pedido.setPrice(preco);

      try {
         ordersDAO.updateOrder(pedido);
         System.out.println("Pedido atualizado com sucesso!");
      } catch (SQLException e) {
         System.err.println("Erro ao atualizar pedido: " + e.getMessage());
      }
   }

   public void excluirCliente(int id) {
      try {
         Customer cliente = customerDAO.getCustomerById(id);
         if (cliente != null) {
            customerDAO.deleteCustomer(id);
            System.out.println("Cliente excluído com sucesso!");
         } else {
            System.out.println("Cliente não encontrado.");
         }
      } catch (SQLException e) {
         System.err.println("Erro ao excluir cliente: " + e.getMessage());
      }
   }

   public void excluirPedido(int numero) {
      try {
         Orders pedido = ordersDAO.getOrderByNumber(numero);
         if (pedido != null) {
            ordersDAO.deleteOrder(numero);
            System.out.println("Pedido excluído com sucesso!");
         } else {
            System.out.println("Pedido não encontrado.");
         }
      } catch (SQLException e) {
         System.err.println("Erro ao excluir pedido: " + e.getMessage());
      }
   }

   public void excluirTodosClientes() {
      try {
         customerDAO.deleteAllCustomers();
         System.out.println("Todos os clientes foram excluídos com sucesso!");
      } catch (SQLException e) {
         System.err.println("Erro ao excluir clientes: " + e.getMessage());
      }
   }

   public void consultarClientePorId(int id) {
      try {
         Customer cliente = customerDAO.getCustomerById(id);

         if (cliente != null) {

         } else {
            System.out.println("Cliente não encontrado.");
         }
      } catch (SQLException e) {
         System.err.println("Erro ao consultar cliente: " + e.getMessage());
      }
   }

   public void consultarClientePorNome(String nome) {
      try {
         List<Customer> cliente = customerDAO.getCustomersByName(nome);

         if (cliente != null) {
            System.out.println("Cliente encontrado: ");
            for (Customer customer : cliente) {
               System.out.print("[id = " + customer.getId() + ", ");
               System.out.print("name =" + customer.getName() + ", ");
               System.out.print("city =" + customer.getCity() + ", ");
               System.out.print("state =" + customer.getState() + "]\n");
            }
         } else {
            System.out.println("Cliente não encontrado.");
         }
      } catch (SQLException e) {
         System.err.println("Erro ao consultar cliente: " + e.getMessage());
      }
   }

   public void consultarPedidoPorNumero(int numero) {
      try {
         Orders pedido = ordersDAO.getOrderByNumber(numero);

         if (pedido != null) {
            System.out.println("Pedido encontrado: " + pedido.toString());
         } else {
            System.out.println("Pedido não encontrado.");
         }
      } catch (SQLException e) {
         System.err.println("Erro ao consultar pedido: " + e.getMessage());
      }
   }

   public void consultarClientesOrdenadosPorId() {
      try {
         List<Customer> clientes = customerDAO.getAllCustomersOrderedById();
         for (Customer customer : clientes) {
            System.out.println(customer.toString());
         }
      } catch (SQLException e) {
         System.err.println("Erro ao consultar clientes: " + e.getMessage());
      }
   }

   public void consultarClientesOrdenadosPorNome() {
      try {
         List<Customer> clientes = customerDAO.getAllCustomersOrderedByName();
         for (Customer customer : clientes) {
            System.out.println(customer.toString());
         }
      } catch (SQLException e) {
         System.err.println("Erro ao consultar clientes: " + e.getMessage());
      }
   }

   public void consultarPedidosOrdenadosPorNumero() {
      try {
         List<Orders> pedidos = ordersDAO.getAllOrdersOrderedByNumber();
         for (Orders order : pedidos) {
            System.out.println(order.toString());
         }
      } catch (SQLException e) {
         System.err.println("Erro ao consultar pedidos: " + e.getMessage());
      }
   }

   public void consultarPedidosDosClientesOrdenadosPorNome() {
      try {
         List<Customer> clientes = customerDAO.getAllCustomersOrderedByName();
         for (Customer customer : clientes) {
            System.out.println(customer.toString());
            List<Orders> pedidos = ordersDAO.getOrdersByCustomerId(customer.getId());
            for (Orders order : pedidos) {
               System.out.println("   " + order.toString());
            }
            BigDecimal totalPedidos = pedidos.stream().map(Orders::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            System.out.println("Total de pedidos: " + totalPedidos);
            System.err.println("--------------------------------------------------");
         }
      } catch (SQLException e) {
         System.err.println("Erro ao consultar pedidos: " + e.getMessage());
      }
   }

}