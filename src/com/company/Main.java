package com.company;

import org.h2.tools.Server;

import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTable(conn);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            boolean keepRunning = true;
            while (keepRunning) {
                System.out.println("1. Create to-do item");
                System.out.println("2. Toggle to-do item");
                System.out.println("3. List to-do items");
                System.out.println("4. Delete to-do item");
                System.out.println("5. Logout");

                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        addToDo(conn,scanner);
                        break;
                    case "2":
                        toggleToDo(conn,scanner);
                        break;
                    case "3":
                        listToDo(conn);
                        break;
                    case "4":
                        deleteToDo(conn,scanner);
                        break;
                    case "5":
                       keepRunning = false;
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            }
        }
    }

    public static void addToDo(Connection conn,Scanner scanner) throws SQLException {
        System.out.println("Enter your to-do item:");
        String text = scanner.nextLine();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO to_dos VALUES(NULL,?,?)");
        stmt.setString(1,text);
        stmt.setBoolean(2,false);
        stmt.execute();
    }

    public static void toggleToDo(Connection conn,Scanner scanner) throws SQLException {
        System.out.println("Which item do you want to toggle?");
        int i = Integer.valueOf(scanner.nextLine());
        PreparedStatement stmt = conn.prepareStatement("UPDATE to_dos SET is_done = NOT is_done WHERE id = ?");
        stmt.setInt(1,i);
        stmt.execute();
    }

    public static void listToDo(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM to_dos");
        ResultSet results = stmt.executeQuery();
        ArrayList<Item> items = new ArrayList<>();
        while (results.next()) {
            int id = results.getInt("id");
            String text = results.getString("text");
            Boolean isDone = results.getBoolean("is_done");
            Item item = new Item(id,text,isDone);
            items.add(item);
        }
        for (int j = 0; j < items.size(); j++) {
            Item item3 = items.get(j);
            String checkbox = "[ ]";
            if (item3.isDone) {
                checkbox = "[x]";
            }
            //System.out.println(checkbox + " " + numb + ". " + item3.text);  string formatting
            System.out.printf("%s %s. %s\n", checkbox, item3.id, item3.text);
        }
    }

    public static void createTable (Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS to_dos (id IDENTITY,text VARCHAR,is_done BOOLEAN)");
    }

    public static void deleteToDo (Connection conn,Scanner scanner) throws SQLException {
        System.out.println("Which item do you want to delete?");
        int i = Integer.valueOf(scanner.nextLine());
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM to_dos WHERE id = ?");
        stmt.setInt(1,i);
        stmt.execute();
    }
}
