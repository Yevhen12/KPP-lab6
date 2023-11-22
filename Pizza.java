import java.io.Serializable;
import java.util.List;

public class Pizza implements Serializable {
    private String name;
    private double weight;
    private double cost;
    private List<String> ingredients;

    public Pizza(String name, double weight, double cost, List<String> ingredients) {
        this.name = name;
        this.weight = weight;
        this.cost = cost;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

   public String toString() {
        return "\n Pizza " +
                " name = '" + name + '\'' +
                " weight = " + weight +
                " cost = " + cost +
                " ingredients = " + ingredients +
                "\n";
    }
}
