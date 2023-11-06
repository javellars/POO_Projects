package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import connection.MemoryDBConnection;
import model.Customer;


public class Customer_Mem_DAO extends AbstractCustomerDAO
   {
   private MemoryDBConnection databaseRef;

   public Customer_Mem_DAO(MemoryDBConnection databaseRef)
      {
      super();
      this.databaseRef = databaseRef;
      }

   @Override
   public List<Customer> getAllCustomersOrderedByName() throws SQLException
      {
      List<Customer> customers = new ArrayList<>();
      customers.addAll(databaseRef.getCustomerList());
      return customers;
      }
   
   @Override
   public List<Customer> getAllCustomersOrderedById() throws SQLException
   {
	    List<Customer> customers = new ArrayList<>(databaseRef.getCustomerList());
	    // Ordenar a lista de clientes pelo identificador (ID)
	    customers.sort((c1, c2) -> Integer.compare(c1.getId(), c2.getId()));
	    return customers;
	}

   
   @Override
   public List<Customer> getCustomersByName(String name) {
	    List<Customer> customers = new ArrayList<>();
	    
	    for (Customer customer : databaseRef.getCustomerList()) {
	        if (customer.getName().toLowerCase().contains(name.toLowerCase())) {
	            customers.add(customer);
	        }
	    }

	    return customers;
	}


   @Override
   public Customer getCustomerById(int customerId) throws SQLException
      {
      Customer customer = null;
      Iterator<Customer> iterator = databaseRef.getCustomerList().iterator();

      while (iterator.hasNext())
         {
         Customer buffer = iterator.next();
         if (buffer.getId() == customerId)
            {
            customer = buffer;
            }
         }
      return customer;
      }

   @Override
   public void addCustomer(Customer customer) throws SQLException
      {
      databaseRef.getCustomerList().add(customer);
      }

   @Override
   public void updateCustomer(Customer customer) throws SQLException
      {
      ArrayList<Customer> customers = databaseRef.getCustomerList();

      for (int index = 0; index < customers.size(); index++)
         {
         if (customers.get(index).getId() == customer.getId())
            {
            customers.set(index, customer);
            break;
            }
         }
      }

   @Override
   public void deleteCustomer(int customerId) throws SQLException
      {
      ArrayList<Customer> customers = databaseRef.getCustomerList();

      for (int index = 0; index < customers.size(); index++)
         {
         if (customers.get(index).getId() == customerId)
            {
            customers.remove(index);
            break;
            }
         }
      }

   @Override
   public void deleteAllCustomers() throws SQLException
      {
      databaseRef.getCustomerList().clear();
      }
   }

