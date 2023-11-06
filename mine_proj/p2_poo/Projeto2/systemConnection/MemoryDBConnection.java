package SystemConnection;

import java.util.ArrayList;

import DTO.Customer;
import DTO.Orders;

public class MemoryDBConnection
   {
   private ArrayList<Customer> customersDB;
   private ArrayList<Orders>   ordersDB;

   public MemoryDBConnection()
      {
      super();
      customersDB = new ArrayList<>();
      ordersDB = new ArrayList<>();
      }

   public ArrayList<Customer> getCustomerList()
      {
      return (customersDB);
      }

   public ArrayList<Orders> getOrderList()
      {
      return (ordersDB);
      }

   public void close()
      {
      customersDB.clear();
      customersDB = null;
      ordersDB.clear();
      ordersDB = null;
      }
   }
