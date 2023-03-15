package ibf2022.ssf.day18.model;

import java.io.Serializable;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Order implements Serializable{
    private static final long serialVersionUID=1L;

    private float totalCost = -1;
    private String orderId;
    private Pizza pizza;
    private Delivery delivery;

    public float getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public Pizza getPizza() {
        return pizza;
    }
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
    public Delivery getDelivery() {
        return delivery;
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public static JsonObject create(String json) {
        JsonReader jr = Json.createReader(new StringReader(json));
        return jr.readObject();
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
        .add("orderId", orderId)
        .add("name", this.getDelivery().getName())
        .add("address", this.getDelivery().getAddress())
        .add("phone", this.getDelivery().getPhone())
        .add("rush", this.getDelivery().isRush())
        .add("comments", this.getDelivery().getComments())
        .add("pizza", this.getPizza().getPizza())
        .add("size", this.getPizza().getSize())
        .add("quantity", this.getPizza().getQuantity())
        .build();
    }

}
