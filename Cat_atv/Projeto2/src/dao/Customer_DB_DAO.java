package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Customer;

public class Customer_DB_DAO extends AbstractCustomerDAO
   {
   private Connection connection;

   public Customer_DB_DAO(Connection connection)
      {
      super();
      this.connection = connection;
      }

   @Override
   public List<Customer> getAllCustomersOrderedByName() throws SQLException
      {
      List<Customer> customers = new ArrayList<>();
      String query = "SELECT * FROM Customer ORDER BY name";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query);
               ResultSet resultSet = preparedStatement.executeQuery())
         {

         while (resultSet.next())
            {
            Customer customer = new Customer();
            customer.setId(resultSet.getInt("id"));
            customer.setName(resultSet.getString("name"));
            customer.setCity(resultSet.getString("city"));
            customer.setState(resultSet.getString("state"));
            customers.add(customer);
            }
         }

      return customers;
      }
   
   @Override
   public List<Customer> getAllCustomersOrderedById() throws SQLException {
       List<Customer> customers = new ArrayList<>();

       // Defina sua consulta SQL para recuperar clientes ordenados por ID
       String query = "SELECT * FROM customers ORDER BY id";

       try (PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {

           while (resultSet.next()) {
               int id = resultSet.getInt("id");
               String name = resultSet.getString("name");
               String city = resultSet.getString("city");
               String state = resultSet.getString("state");

               Customer customer = new Customer(id, name, city, state);
               customers.add(customer);
           }
       }

       return customers;
   }
   
   @Override
   public List<Customer> getCustomersByName(String name) throws SQLException {
	    List<Customer> customers = new ArrayList<>();
	    
	    // Consulta SQL para buscar clientes por nome
	    String query = "SELECT * FROM customers WHERE name LIKE ?";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, "%" + name + "%");
	        
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                int customerId = resultSet.getInt("id");
	                String customerName = resultSet.getString("name");
	                String city = resultSet.getString("city");
	                String state = resultSet.getString("state");
	                customers.add(new Customer(customerId, customerName, city, state));
	            }
	        }
	    }
	    
	    return customers;
	}

   @Override
   public Customer getCustomerById(int customerId) throws SQLException
      {
      String query = "SELECT * FROM Customer WHERE id = ?";
      Customer customer = null;

      try (PreparedStatement preparedStatement = connection.prepareStatement(query))
         {
         preparedStatement.setInt(1, customerId);
         ResultSet resultSet = preparedStatement.executeQuery();

         if (resultSet.next())
            {
            customer = new Customer();
            customer.setId(resultSet.getInt("id"));
            customer.setName(resultSet.getString("name"));
            customer.setCity(resultSet.getString("city"));
            customer.setState(resultSet.getString("state"));
            }
         }

      return customer;
      }

   @Override
   public void addCustomer(Customer customer) throws SQLException
      {
         {
         String query = "INSERT INTO Customer (id, name, city, state) VALUES (?, ?, ?, ?)";

         try (PreparedStatement preparedStatement = connection.prepareStatement(query))
            {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getCity());
            preparedStatement.setString(4, customer.getState());

            preparedStatement.executeUpdate();
            }
         }
      }

   @Override
   public void updateCustomer(Customer customer) throws SQLException
      {
      String query = "UPDATE Customer SET name = ?, city = ?, state = ? WHERE id = ?";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query))
         {
         preparedStatement.setString(1, customer.getName());
         preparedStatement.setString(2, customer.getCity());
         preparedStatement.setString(3, customer.getState());
         preparedStatement.setInt(4, customer.getId());

         preparedStatement.executeUpdate();
         }
      }

   @Override
   public void deleteCustomer(int customerId) throws SQLException
      {
      String query = "DELETE FROM Customer WHERE id = ?";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query))
         {
         preparedStatement.setInt(1, customerId);
         preparedStatement.executeUpdate();
         }
      }

   @Override
   public void deleteAllCustomers() throws SQLException
      {
      String query = "DELETE FROM Customer";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query))
         {
         preparedStatement.executeUpdate();
         }
      }
   }

