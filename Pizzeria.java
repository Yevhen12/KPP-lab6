import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Pizzeria implements Serializable {
  private List<Pizza> pizzas;
  private List<Customer> customers;

  public Pizzeria() {
    this.pizzas = new ArrayList<>();
    this.customers = new ArrayList<>();
  }

  public void addPizza(Pizza pizza) {
    pizzas.add(pizza);
  }

  public void addCustomer(Customer customer) {
    customers.add(customer);
  }

  public List<Order> sortOrdersByDeliveryTime() {
    return customers.stream()
        .flatMap(customer -> customer.getOrders().stream())
        .sorted(Comparator.comparing(Order::getDeliveryTime))
        .collect(Collectors.toList());
  }

  public List<String> getAddressesForCustomersWithMultiplePizzas() {
    List<String> addresses = customers.stream()
        .filter(customer -> customer.getOrders().size() > 2)
        .map(Customer::getDeliveryAddress)
        .collect(Collectors.toList());

    return new ArrayList<>(addresses);
  }

  public long countCustomersWithPizza(String pizzaName) {
    return customers.stream()
        .filter(customer -> customer.getOrders().stream()
            .anyMatch(order -> order.getPizza().getName().equals(pizzaName)))
        .count();
  }

  public Customer findCustomerWithMostPizzas() {
    Optional<Customer> customerWithMostPizzas = customers.stream()
            .max(Comparator.comparingInt(customer -> customer.getOrders().size()));

    return customerWithMostPizzas.orElse(null);
}

  public Map<Pizza, List<Customer>> getPizzasWithCustomers() {
    return customers.stream()
        .flatMap(customer -> customer.getOrders().stream())
        .collect(Collectors.groupingBy(
            Order::getPizza,
            Collectors.mapping(Order::getCustomer, Collectors.toList())
        ));
}

public List<Order> getUnfulfilledOrders(Date currentTime, int timeoutMinutes) {
  return customers.stream()
      .flatMap(customer -> customer.getOrders().stream()
          .filter(order -> order.getDeliveryTime().after(currentTime)
              && order.getDeliveryTime().getTime() - currentTime.getTime() > timeoutMinutes * 60 * 1000))
      .map(order -> {
          Calendar rescheduleTime = Calendar.getInstance();
          rescheduleTime.setTime(order.getDeliveryTime());
          rescheduleTime.add(Calendar.HOUR, 1);
          order.setRescheduleTime(rescheduleTime.getTime());
          return order;
      })
      .collect(Collectors.toList());
}

  public List<Customer> getCustomers() {
    return this.customers;
  }

  public List<Pizza> getPizzas() {
    return this.pizzas;
  }

  @Override
  public String toString() {
    return "Pizzeria " +
        "pizzas = " + pizzas +
        "customers = " + customers +
        "\n";
  }
}
