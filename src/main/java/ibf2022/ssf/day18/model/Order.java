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

    public Order(Pizza pizza, Delivery delivery) {
        this.pizza = pizza;
        this.delivery = delivery;
    }

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

    public String getName() {
        return this.getDelivery().getName(); }
    public String getPizzaName() {
        return this.getPizza().getPizza(); }
    public String getAddress() {
        return this.getDelivery().getAddress(); }
    public String getPhone() {
        return this.getDelivery().getPhone(); }
    public boolean getRush() {
        return this.getDelivery().isRush(); }
    public String getComments() {
        return this.getDelivery().getComments(); }
    public String getSize() {
        return this.getPizza().getSize(); }
    public int getQuantity() {
        return this.getPizza().getQuantity(); }

    public float getPizzaCost() {
        return this.getRush() ? this.getTotalCost() - 2 : this.getTotalCost();
    }

    public static JsonObject toJSON(String json) {
        JsonReader jr = Json.createReader(new StringReader(json));
        return jr.readObject();
    }

    public static Order create(String json) {
        JsonObject obj = toJSON(json);
        Pizza pizza = Pizza.create(obj);
        Delivery delivery = Delivery.create(obj);
        Order order = new Order(pizza, delivery);
        order.setOrderId(obj.getString("orderId"));
        order.setTotalCost((float) obj.getJsonNumber("total").doubleValue());
        return order;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
        .add("orderId", orderId)
        .add("name", this.getName())
        .add("address", this.getAddress())
        .add("phone", this.getPhone())
        .add("rush", this.getRush())
        .add("comments", this.getComments())
        .add("pizza", this.getPizzaName())
        .add("size", this.getPizza().getSize())
        .add("quantity", this.getPizza().getQuantity())
        .add("totalCost", this.getTotalCost())
        .build();
    }

}
