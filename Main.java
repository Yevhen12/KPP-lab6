import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

  private static final String CUSTOMERS_FILE = "customers.ser";
  private static final String PIZZAS_FILE = "pizzas.ser";

  public static void main(String[] args) {
    Pizzeria pizzeria = new Pizzeria();
    List<Pizza> pizzas = readPizzasFromFile("pizzas.txt");

    List<Customer> customersList = readCustomersFromFile("customers.txt");

    serializeCollection(PIZZAS_FILE, pizzas);
    serializeCollection(CUSTOMERS_FILE, customersList);

    Order order1 = new Order(pizzas.get(0), customersList.get(0), parseTime("12:00"));
    Order order2 = new Order(pizzas.get(1), customersList.get(0), parseTime("13:30"));
    Order order3 = new Order(pizzas.get(0), customersList.get(1), parseTime("14:45"));
    Order order4 = new Order(pizzas.get(0), customersList.get(0), parseTime("14:50"));

    customersList.get(0).getOrders().add(order1);
    customersList.get(1).getOrders().add(order2);
    customersList.get(1).getOrders().add(order3);
    customersList.get(1).getOrders().add(order4);

    pizzeria.getCustomers().addAll(customersList);
    pizzeria.getPizzas().addAll(pizzas);

    Map<Pizza, List<Customer>> pizzasWithCustomers2 = pizzeria.getPizzasWithCustomers();

    pizzasWithCustomers2.forEach((pizza, customers) -> {
      System.out.println("Pizza: " + pizza.getName());
      System.out.println("Customers:");
      customers.forEach(customer -> System.out.println(" - " + customer.getDeliveryAddress()));
      System.out.println();
    });

    System.out.println(pizzeria.getCustomers());

    Scanner scanner = new Scanner(System.in);

    int choice;
    do {
      displayMenu();
      System.out.print("Enter your choice (1-6, 0 to exit): ");
      choice = scanner.nextInt();
      scanner.nextLine();

      switch (choice) {
        case 1:
          List<Order> sortedOrders = pizzeria.sortOrdersByDeliveryTime();
          System.out.println("Sorted Orders: " + sortedOrders);
          break;
        case 2:
          List<String> addresses = pizzeria.getAddressesForCustomersWithMultiplePizzas();
          System.out.println("Addresses for customers with more than 2 pizzas: " + addresses);
          break;
        case 3:
          System.out.print("Enter pizza name: ");
          String pizzaName = scanner.nextLine();
          long pizzaCount = pizzeria.countCustomersWithPizza(pizzaName);
          System.out.println("Number of customers who ordered " + pizzaName + ": " + pizzaCount);
          break;
        case 4:
          Customer customerWithMostPizzas = pizzeria.findCustomerWithMostPizzas();
          System.out.println("Customer with most pizzas: " + customerWithMostPizzas.getName());
          break;
        case 5:
          Map<Pizza, List<Customer>> pizzasWithCustomers = pizzeria.getPizzasWithCustomers();

          pizzasWithCustomers.forEach((pizza, customers) -> {
            System.out.println("Pizza: " + pizza.getName());
            System.out.println("Customers:");
            customers.forEach(
                customer -> System.out.println(" - " + customer.getName() + " - " + customer.getDeliveryAddress()));
            System.out.println();
          });
          break;
        case 6:
          System.out.print("Enter current time (HH:mm): ");
          String timeString = scanner.nextLine();
          Date currentTime = parseTime(timeString);
          System.out.print("Enter timeout in minutes: ");
          int timeoutMinutes = scanner.nextInt();
          List<Order> unfulfilledOrders = pizzeria.getUnfulfilledOrders(currentTime, timeoutMinutes);
          for (Order order : unfulfilledOrders) {
            System.out.println("Order Location: " + order.getCustomer().getDeliveryAddress() + ", Order expected delivery time: " +
            order.getDeliveryTime() + ", Rescheduled Time: " + order.getRescheduleTime());
        }
          break;
        case 0:
          System.out.println("Exiting the program. Goodbye!");
          break;
        default:
          System.out.println("Invalid choice. Please enter a number between 0 and 6.");
          break;
      }

    } while (choice != 0);

    scanner.close();
  }

  private static void displayMenu() {
    System.out.println("\n*** Pizzeria Management System ***");
    System.out.println("1. Sort Orders by Delivery Time");
    System.out.println("2. Get Addresses for Customers with Multiple Pizzas");
    System.out.println("3. Count Customers with a Specific Pizza");
    System.out.println("4. Find Customer with Most Pizzas");
    System.out.println("5. Get Pizzas with Customers");
    System.out.println("6. Get Unfulfilled Orders");
    System.out.println("0. Exit");
  }

  private static Date parseTime(String timeString) {
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
      return dateFormat.parse(timeString);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static List<Pizza> readPizzasFromFile(String fileName) {
    List<Pizza> pizzas = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(fileName))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] pizzaData = line.split(",");
        String name = pizzaData[0];
        int weight = Integer.parseInt(pizzaData[1]);
        double cost = Double.parseDouble(pizzaData[2]);
        List<String> ingredients = new ArrayList<>();
        for (int i = 3; i < pizzaData.length; i++) {
          ingredients.add(pizzaData[i]);
        }
        Pizza pizza = new Pizza(name, weight, cost, ingredients);
        pizzas.add(pizza);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return pizzas;
  }

  private static List<Customer> readCustomersFromFile(String fileName) {
    List<Customer> customers = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(fileName))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] customerData = line.split(",");
        int id = Integer.parseInt(customerData[0]);
        String name = customerData[1];
        String address = customerData[2];
        Customer customer = new Customer(id, name, address, new ArrayList<>());
        customers.add(customer);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return customers;
  }

  private static <T> void serializeCollection(String fileName, List<T> collection) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
      oos.writeObject(collection);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
