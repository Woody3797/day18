package ibf2022.ssf.day18.model;

import java.io.Serializable;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Delivery implements Serializable{
    private static final long serialVersionUID=1L;

    @NotNull(message = "Must provide a name")
    @Size(min = 3, message = "Name cannot be less than 3 characters")
    private String name;

    @NotNull(message = "Must provide an address")
    @NotEmpty(message = "Must type an address")
    private String address;

    @NotNull(message = "Must provide phone number")
    @Pattern(regexp = "^(0-9)(8,)$", message = "Must be a valid phone number")
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
