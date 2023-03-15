package ibf2022.ssf.day18.model;

import java.io.Serializable;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Pizza implements Serializable{
    private static final long serialVersionUID=1L;
    
    @NotNull(message = "Must select a pizza")
    private String pizza;

    @NotNull(message = "Must select a size")
    private String size;

    @Min(value = 1, message = "Must order at least 1")
    @Max(value = 10, message = "Too many pizzas!")
    private int quantity;


    public String getPizza() {
        return pizza;
    }
    public void setPizza(String pizza) {
        this.pizza = pizza;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static Pizza create(JsonObject obj) {
        Pizza pizza = new Pizza();
        pizza.setPizza(obj.getString("pizza"));
        pizza.setSize(obj.getString("size"));
        pizza.setQuantity(obj.getInt("quantity"));
        return pizza;
    }

}
