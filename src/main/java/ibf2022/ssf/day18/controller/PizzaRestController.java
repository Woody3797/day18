package ibf2022.ssf.day18.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.ssf.day18.model.Order;
import ibf2022.ssf.day18.service.PizzaService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("/order")
public class PizzaRestController {
    
    @Autowired
    private PizzaService pizzaService;

    @GetMapping(path = "{orderId}")
    public ResponseEntity<String> getOrder(@PathVariable String orderId) {
        Optional<Order> order = pizzaService.getOrderById(orderId);
        if (order.isEmpty()) {
            JsonObject error = Json.createObjectBuilder()
            .add("message", "order %s not found".formatted(orderId))
            .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
        return ResponseEntity.ok(order.get().toJSON().toString());
    }
}
