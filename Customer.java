import java.io.Serializable;
import java.util.List;

public class Customer implements Serializable {
    private int number;
    private String deliveryAddress;
    private String name;
    private List<Order> orders;

    public Customer(int number, String name, String deliveryAddress, List<Order> orders) {
        this.number = number;
        this.deliveryAddress = deliveryAddress;
        this.orders = orders;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getName() {
        return name;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer " +
                " name = " + name +
                "number = " + number +
                " deliveryAddress = " + deliveryAddress + '\'' +
                " orders = " + orders +
                "\n";
    }
}
