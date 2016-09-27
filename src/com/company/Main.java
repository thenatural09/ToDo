package com.company;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HashMap<String, ArrayList<Item>> users = new HashMap<>();

//        ArrayList<Item> items = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter name");
            String name = scanner.nextLine();

            ArrayList<Item> items = users.get(name);
            if (items == null) {
                items = new ArrayList<>();
                users.put(name, items);
            }

            boolean keepRunning = true;
            while (keepRunning) {
                System.out.println("1. Create to-do item");
                System.out.println("2. Toggle to-do item");
                System.out.println("3. List to-do items");
                System.out.println("4. Logout");

                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        addToDo(scanner,items);
                        break;
                    case "2":
                        toggleToDo(scanner,items);
                        break;
                    case "3":
                        listToDo(items);
                        break;
                    case "4":
                       keepRunning = false;
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            }
        }
    }

    public static void addToDo(Scanner scanner,ArrayList<Item> items) {
        System.out.println("Enter your to-do item:");
        String text = scanner.nextLine();
        //need to create item object and then add it to items array list
        Item item = new Item(text, false);
        items.add(item);
    }

    public static void toggleToDo(Scanner scanner,ArrayList<Item> items) {
        System.out.println("Which item do you want to toggle?");
        int i = Integer.valueOf(scanner.nextLine());
        //need to create new object which to change
        Item item2 = items.get(i - 1);
        //need to change from false to true or true to false
        item2.isDone = !item2.isDone;
    }

    public static void listToDo(ArrayList<Item> items) {
        for (int j = 0; j < items.size(); j++) {
            Item item3 = items.get(j);
            int numb = j + 1;
            String checkbox = "[ ]";
            if (item3.isDone) {
                checkbox = "[x]";
            }
            //System.out.println(checkbox + " " + numb + ". " + item3.text);  string formatting
            System.out.printf("%s %s. %s\n", checkbox, numb, item3.text);
        }
    }
}
