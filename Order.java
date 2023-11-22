import java.util.Date;

public class Order {
    private Pizza pizza;
    private Customer customer;
    private Date deliveryTime;
    private Date rescheduledTime;

    public Order(Pizza pizza, Customer customer, Date deliveryTime) {
        this.pizza = pizza;
        this.customer = customer;
        this.deliveryTime = deliveryTime;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public Date getRescheduleTime() {
        return rescheduledTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setRescheduleTime(Date rescheduledTime) {
        this.rescheduledTime = rescheduledTime;
    }

    @Override
    public String toString() {
        return "Order " +
                "pizza = " + pizza +
                " deliveryTime = " + deliveryTime +
                "\n";
    }
}