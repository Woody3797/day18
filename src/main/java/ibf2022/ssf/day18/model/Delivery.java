package ibf2022.ssf.day18.model;

import java.io.Serializable;

import jakarta.json.JsonObject;

public class Delivery implements Serializable{
    private static final long serialVersionUID=1L;

    private String name;
    private String address;
    private String phone;
    private boolean rush = false;
    private String comments;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean isRush() {
        return rush;
    }
    public void setRush(boolean rush) {
        this.rush = rush;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public static Delivery create(JsonObject obj) {
        Delivery delivery = new Delivery();
        delivery.setName(obj.getString("name"));
        delivery.setAddress(obj.getString("address"));
        delivery.setPhone(obj.getString("phone"));
        delivery.setRush(obj.getBoolean("rush"));
        delivery.setComments(obj.getString("comments"));
        return delivery;
    }
}
