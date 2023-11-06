package system;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//import java.util.Random;

import connection.MariaDBConnection;
import connection.MemoryDBConnection;
import dao.AbstractCustomerDAO;
import dao.AbstractOrderDAO;
import dao.Customer_DB_DAO;
import dao.Customer_Mem_DAO;
import dao.Order_DB_DAO;
import dao.Order_Mem_DAO;
import model.Customer;
import model.Orders;
import view.Menu;
import view.InfoMenu;
import view.CustomerMenu;
import view.OrderMenu;

public class Controller
   {
   private AbstractCustomerDAO customerDAO        = null;
   private AbstractOrderDAO    ordersDAO          = null;
   private MariaDBConnection   myDBConnection     = null;
   private MemoryDBConnection  memoryDBConnection = null;
   private DataBaseType        selectedDataBase   = DataBaseType.INVALID;
   private Menu menu;
   private InfoMenu info;
   private CustomerMenu customerMenu;
   private OrderMenu orderMenu;

   public Controller(DataBaseType selectedDataBase)
      {
      super();
      this.selectedDataBase = selectedDataBase;
      menu = new Menu();
      info = new InfoMenu();
      customerMenu = new CustomerMenu();
      orderMenu = new OrderMenu();
      }

   private void openConnection()
      {
      switch (selectedDataBase)
         {
         case MEMORY:
            {
            memoryDBConnection = new MemoryDBConnection();
            this.customerDAO = new Customer_Mem_DAO(memoryDBConnection);
            this.ordersDAO = new Order_Mem_DAO(memoryDBConnection);
            }
            break;
         case MARIADB:
            {
            myDBConnection = new MariaDBConnection();
            this.customerDAO = new Customer_DB_DAO(myDBConnection.getConnection());
            this.ordersDAO = new Order_DB_DAO(myDBConnection.getConnection());
            }
            break;
         default:
            {
            System.out.println("Database selection not supported.");
            throw new InvalidParameterException("Selector is unspecified: " + selectedDataBase);
            }
         }
      }

   private void closeConnection()
      {
      if (myDBConnection != null)
         {
         myDBConnection.close();
         }
      if (memoryDBConnection != null)
         {
         memoryDBConnection.close();
         }
      }

   public void start()
      {
      openConnection();
      menu.displayWelcomeMessage();
      
      int choice;
      do {
    	  menu.displayMainMenu();
    	  choice = menu.readUserChoice();
    	  
    	  switch(choice) {
    	  case 1:
    		  handleCustomersMenu();
    		  break;
    	  case 2:
    		  handleOrdersMenu();
    		  break;
    	  case 3:
    		  handleReportsMenu();
    		  break;
    	  case 4:
    		  handleInfoMenu();
    		  break;
    	  case 5:
    		  break;
    	default: 
    		System.err.println("Opção inválida. Escolha uma opção válida.");
    	  }
      } while (choice != 5);

      closeConnection();
      menu.displayGoodbyeMessage();
      menu.closeScanner();
      }

   private void handleCustomersMenu() {
	   int choice;
	   do {
		   menu.displayCustomersMenu();
		   choice = menu.readUserChoice();
		   
		   switch(choice) {
	    	  case 1:
	                int customerId = customerMenu.readCustomerId();
	                String name = customerMenu.readCustomerName();
	                String city = customerMenu.readCustomerCity();
	                String state = customerMenu.readCustomerState();

	                Customer newCustomer = new Customer(customerId, name, city, state);

	                try {
	                    customerDAO.addCustomer(newCustomer);
	                    System.out.println("\nCliente adicionado com sucesso!");
	                } catch (SQLException e) {
	                    System.err.println("Erro ao adicionar o cliente: " + e.getMessage());
	                }
	    		  break;
	    		  
	    	  case 2:
	                int customerIdToSearch = customerMenu.readCustomerIdToSearch();

	                try {
	                    Customer foundCustomer = customerDAO.getCustomerById(customerIdToSearch);

	                    if (foundCustomer != null) {
	                    	System.out.println("\nInformações do cliente:");
	                        customerMenu.displayCustomerInfo(foundCustomer);
	                    } else {
	                    	System.out.println("Cliente não encontrado.");
	                    }
	                } catch (SQLException e) {
	                    System.err.println("Erro ao consultar o cliente: " + e.getMessage());
	                }
	    		  break;
	    		  
	    	  case 3:
	    		  	System.out.println("");
	    		    String customerNameToSearch = customerMenu.readCustomerName();

	    		    try {
	    		        List<Customer> customers = customerDAO.getCustomersByName(customerNameToSearch);

	    		        if (!customers.isEmpty()) {
	    		            System.out.println("\nClientes encontrados:");
	    		            for (Customer foundCustomer : customers) {
	    		                customerMenu.displayCustomerInfo(foundCustomer);
	    		            }
	    		        } else {
	    		            System.out.println("Nenhum cliente encontrado com esse nome.");
	    		        }
	    		    } catch (SQLException e) {
	    		        System.err.println("Erro ao consultar os clientes: " + e.getMessage());
	    		    }
	    		  break;
	    		  
	    	  case 4:
	    		    int customerIdToDelete = customerMenu.readCustomerId(); 
	    		    try {
	    		        customerDAO.deleteCustomer(customerIdToDelete);
	    		        System.out.println("\nCliente excluído com sucesso!");
	    		    } catch (SQLException e) {
	    		        System.err.println("Erro ao excluir o cliente: " + e.getMessage());
	    		    }
	    		  break;
	    		  
	    	  case 5:
	    		  break;
	    	default: 
	    		System.err.println("Opção inválida. Escolha uma opção válida.");
	    	  }
	      } while (choice != 5);
	   }

   private void handleOrdersMenu(){
	   int choice;
	   do {
		   menu.displayOrdersMenu();
		   choice = menu.readUserChoice();
		   
		   switch(choice) {
	    	  case 1:
	    		// Inserir pedido para um cliente

	                int customerNumber = orderMenu.readCustomerNumber(); // Use os métodos da classe OrderMenu
	                Customer customer = null;

	                try {
	                    customer = customerDAO.getCustomerById(customerNumber);
	                } catch (SQLException e) {
	                    System.err.println("Erro ao consultar o cliente: " + e.getMessage());
	                    break;
	                }

	                if (customer == null) {
	                    System.err.println("Cliente não encontrado.");
	                    break;
	                }

	                int orderNumber = orderMenu.readOrderNumber(); // Use os métodos da classe OrderMenu
	                String description = orderMenu.readOrderDescription(); // Use os métodos da classe OrderMenu
	                BigDecimal price = orderMenu.readOrderPrice(); // Use os métodos da classe OrderMenu

	                Orders order1 = new Orders(orderNumber, customerNumber, description, price);

	                try {
	                    ordersDAO.addOrder(order1);
	                    System.out.println("\nPedido adicionado com sucesso!");
	                } catch (SQLException e) {
	                    System.err.println("Erro ao adicionar o pedido: " + e.getMessage());
	                }
	    		  break;
	    	  case 2:
	    		// Consultar pedido pelo número
	                int orderNumberToSearch = orderMenu.readOrderNumberToSearch(); // Use os métodos da classe OrderMenu

	                try {
	                    Orders foundOrder = ordersDAO.getOrderByNumber(orderNumberToSearch);

	                    if (foundOrder != null) {
	                        Customer customerForOrder = customerDAO.getCustomerById(foundOrder.getCustomerId());
	                        if (customerForOrder != null) {
	                            System.out.println("\nInformações do pedido:");
	                            System.out.println("Número do Pedido: " + foundOrder.getNumber());
	                            System.out.println("Nome do Cliente: " + customerForOrder.getName());
	                            System.out.println("ID do Cliente: " + customerForOrder.getId());
	                            System.out.println("Descrição do Pedido: " + foundOrder.getDescription());
	                            System.out.println("Preço do Pedido: " + foundOrder.getPrice());
	                        } else {
	                            System.err.println("Cliente associado ao pedido não encontrado.");
	                        }
	                    } else {
	                        System.err.println("Pedido não encontrado.");
	                    }
	                } catch (SQLException e) {
	                    System.err.println("Erro ao consultar o pedido: " + e.getMessage());
	                }
	    		  break;
	    	  case 3:
	    		// Apagar pedido pelo numero
	                int orderNumberToDelete = orderMenu.readOrderNumberToDelete(); // Use os métodos da classe OrderMenu

	                try {
	                    ordersDAO.deleteOrder(orderNumberToDelete);
	                    System.out.println("\nPedido excluído com sucesso!");
	                } catch (SQLException e) {
	                    System.err.println("Erro ao excluir o pedido: " + e.getMessage());
	                }
	    		  break;
	    	  case 4:
	    		  break;
	    	default: 
	    		System.err.println("Opção inválida. Escolha uma opção válida.");
	    	  }
	      } while (choice != 4);
      }

   private void handleReportsMenu(){
	   int choice;
	   do {
		   menu.displayReportsMenu();
		   choice = menu.readUserChoice();
		   
		   switch(choice) {
	    	  case 1:
	    		  System.out.println("\nClientes Ordenados por Identificador");
	    		  List<Customer> customers;
	    		  try {
	    		      customers = customerDAO.getAllCustomersOrderedById();

	    		      if (!customers.isEmpty()) {
	    		          // Ordenar a lista de clientes pelo identificador
	    		          Collections.sort(customers, (c1, c2) -> Integer.valueOf(c1.getId()).compareTo(c2.getId()));
	    		          for (Customer customer : customers) {
	    		              customerMenu.displayCustomerInfo(customer);
	    		          }
	    		      } else {
	    		          System.out.println("Não há clientes cadastrados.");
	    		      }
	    		  } catch (SQLException e) {
	    		      System.err.println("Erro ao obter clientes ordenados por identificador: " + e.getMessage());
	    		  }
	    		  break;
	    	  case 2:
	    		  System.out.println("\nClientes Ordenados por Nome");
	    		    List<Customer> customersByName;
	    		    try {
	    		        customersByName = customerDAO.getAllCustomersOrderedByName();

	    		        if (!customersByName.isEmpty()) {
	    		            // Ordenar a lista de clientes pelo nome
	    		            Collections.sort(customersByName, (c1, c2) -> c1.getName().compareTo(c2.getName()));
	    		            for (Customer customer : customersByName) {
	    		                customerMenu.displayCustomerInfo(customer);
	    		            }
	    		        } else {
	    		            System.out.println("Não há clientes cadastrados.");
	    		        }
	    		    } catch (SQLException e) {
	    		        System.err.println("Erro ao obter clientes ordenados por nome: " + e.getMessage());
	    		    }
	    		  break;
	    	  case 3:
	    		  System.out.println("\nPedidos Ordenados por Número");
	    		  List<Orders> orders;
	    		  try {
	    		      orders = ordersDAO.getAllOrdersOrderedByNumber();

	    		      if (!orders.isEmpty()) {
	    		          Collections.sort(orders, (o1, o2) -> Integer.valueOf(o1.getNumber()).compareTo(o2.getNumber()));
	    		          for (Orders order : orders) {
	    		        	  orderMenu.displayOrderInfo(order);
	    		        	  
	    		        	  Customer customerForOrder = customerDAO.getCustomerById(order.getCustomerId());
	    		              if (customerForOrder != null) {
	    		                  System.out.println("Nome do Cliente: " + customerForOrder.getName());
	    		              } else {
	    		                  System.out.println("Cliente não encontrado para o pedido.");
	    		              }
	    		          }
	    		      } else {
	    		          System.out.println("Não há pedidos cadastrados.");
	    		      }
	    		  } catch (SQLException e) {
	    		      System.err.println("Erro ao obter pedidos ordenados por numero: " + e.getMessage());
	    		  }
	    		  break;
	    	  case 4:
	    		  System.out.println("\nClientes Ordenados por Nome e Seus Pedidos:");

	    		    try {
	    		        // Obtém a lista de clientes ordenados por nome
	    		        List<Customer> customers1 = customerDAO.getAllCustomersOrderedByName();

	    		        if (!customers1.isEmpty()) {
	    		            for (Customer customer : customers1) {
	    		                System.out.print("ID: " + customer.getId() + " | Nome: " + customer.getName() + " | Cidade: " + customer.getCity() + " | Estado: " + customer.getState());
	    		                System.out.println("\nPedidos do Cliente:");

	    		                // Obtém os pedidos do cliente
	    		                List<Orders> orders1 = ordersDAO.getOrdersByCustomerId(customer.getId());

	    		                if (!orders1.isEmpty()) {
	    		                    // Ordenar os pedidos pelo número
	    		                    orders1.sort(Comparator.comparing(Orders::getNumber));

	    		                    BigDecimal totalValue = BigDecimal.ZERO;

	    		                    for (Orders order : orders1) {
	    		                        orderMenu.displayOrderInfo(order);
	    		                        totalValue = totalValue.add(order.getPrice());
	    		                    }

	    		                    System.out.println("Valor total dos pedidos: " + totalValue);
	    		                } else {
	    		                    System.out.println("O cliente não possui pedidos.");
	    		                }
	    		                System.out.println(); // Separador entre clientes
	    		            }
	    		        } else {
	    		            System.out.println("Não há clientes cadastrados.");
	    		        }
	    		    } catch (SQLException e) {
	    		        System.err.println("Erro ao obter clientes e pedidos: " + e.getMessage());
	    		    }
	    		  break;
	    	  case 5:
	    		  break;
	    	default: 
	    		System.err.println("Opção inválida. Escolha uma opção válida.");
	    	  }
	      } while (choice != 5);
      }

   private void handleInfoMenu(){
	   int choice;
	   do {
		   menu.displayInfoMenu();
		   choice = menu.readUserChoice();
		   
		   switch(choice) {
	    	  case 1:
	    		  info.displayAjuda();
	    		  break;
	    	  case 2:
	    		  info.displaySobre();
	    		  break;
	    	  case 3:
	    		  break;
	    	default: 
	    		System.err.println("Opção inválida. Escolha uma opção válida.");
	    	  }
	      } while (choice != 3);
      
      }  
   }


