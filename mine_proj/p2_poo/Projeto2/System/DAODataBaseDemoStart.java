package dataBaseReference.System;

import java.util.Scanner;

import dataBaseReference.DTO.Menu;

public final class DAODataBaseDemoStart {
   public static void main(String[] args) {
      System.out.println("Database demonstration running now...");
      Scanner scanner = new Scanner(System.in);
      Menu menu = new Menu(0);

      try {
         System.out.println("Choose the database type:");
         System.out.println("1. MariaDB");
         System.out.println("2. Memory");
         System.out.print("Enter your choice: ");
         switch (scanner.nextInt()) {
            case 1:
               new Controller(DataBaseType.MARIADB).start();
               menu = new Menu(1);
               menu.executarMenus();
               break;
            case 2:
               new Controller(DataBaseType.MEMORY).start();
               menu = new Menu(2);
               menu.executarMenus();
               break;
            default:
               break;
         }

      } catch (Exception myException) {
         System.out.println("Exception launched. Programa aborted.");
         System.out.println(myException.getMessage());
         myException.printStackTrace();
         System.exit(1);
      }

      System.out.println("Database demonstration stopping now...");
      System.exit(0);
      scanner.close();
   }
}
