package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import connection.MemoryDBConnection;
import model.Orders;


public class Order_Mem_DAO extends AbstractOrderDAO
   {
   private MemoryDBConnection databaseRef;

   public Order_Mem_DAO(MemoryDBConnection databaseRef)
      {
      super();
      this.databaseRef = databaseRef;
      }

   @Override
   public List<Orders> getOrdersByCustomerId(int customerId) throws SQLException
      {
      List<Orders> orders = new ArrayList<>();
      Iterator<Orders> iterator = databaseRef.getOrderList().iterator();

      while (iterator.hasNext())
         {
         Orders buffer = iterator.next();
         if (buffer.getCustomerId() == customerId)
            {
            orders.add(buffer);
            }
         }
      return orders;
      }

   @Override
   public Orders getOrderByNumber(int orderNumber) throws SQLException
      {
      Orders order = null;
      Iterator<Orders> iterator = databaseRef.getOrderList().iterator();

      while (iterator.hasNext())
         {
         Orders buffer = iterator.next();
         if (buffer.getNumber() == orderNumber)
            {
            order = buffer;
            }
         }
      return order;
      }
   
   public List<Orders> getAllOrdersOrderedByNumber() throws SQLException
   {
	    List<Orders> orders = new ArrayList<>(databaseRef.getOrderList());
	    // Ordenar a lista de pedidos pelo numero
	    orders.sort((o1, o2) -> Integer.compare(o1.getNumber(), o2.getNumber()));
	    return orders;
	}

   @Override
   public void addOrder(Orders order) throws SQLException
      {
      databaseRef.getOrderList().add(order);
      }

   @Override
   public void updateOrder(Orders order) throws SQLException
      {
      ArrayList<Orders> orders = databaseRef.getOrderList();

      for (int index = 0; index < orders.size(); index++)
         {
         if (orders.get(index).getNumber() == order.getNumber())
            {
            orders.set(index, order);
            break;
            }
         }
      }

   @Override
   public void deleteOrder(int orderNumber) throws SQLException
      {
      ArrayList<Orders> orders = databaseRef.getOrderList();

      for (int index = 0; index < orders.size(); index++)
         {
         if (orders.get(index).getNumber() == orderNumber)
            {
            orders.remove(index);
            break;
            }
         }
      }

   @Override
   public void deleteAllOrders() throws SQLException
      {
      databaseRef.getOrderList().clear();
      }
   
   }

