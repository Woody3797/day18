package ibf2022.ssf.day18.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.ssf.day18.model.Delivery;
import ibf2022.ssf.day18.model.Order;
import ibf2022.ssf.day18.model.Pizza;
import ibf2022.ssf.day18.repository.PizzaRepository;

@Service
public class PizzaService {
    
    @Autowired
    private PizzaRepository pizzaRepo;

    public static final String[] PIZZA_NAMES = {
        "bella",
        "margherita",
        "marinara",
        "spianatacalabrese",
        "trioformaggio",
    };

    public static final String[] PIZZA_SIZES = {
        "sm",
        "md",
        "lg",
    };

    private final Set<String> pizzaNames;
    private final Set<String> pizzaSizes;

    @Value("${day18.pizza.api.url}")
    private String restPizzaUrl;

    public PizzaService() {
        pizzaNames = new HashSet<>(Arrays.asList(PIZZA_NAMES));
        pizzaSizes = new HashSet<>(Arrays.asList(PIZZA_SIZES));
    }

    public Optional<Order> getOrderById(String orderId) {
        return pizzaRepo.get(orderId);
    }

    public Order createPizzaOrder(Pizza pizza, Delivery delivery) {
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        Order order = new Order(pizza, delivery);
        order.setOrderId(orderId);
        return order;
    }

    public Order savePizzaOrder(Pizza pizza, Delivery delivery) {
        Order order = createPizzaOrder(pizza, delivery);
        calculateCost(order);
        pizzaRepo.save(order);
        return order;
    }

    public float calculateCost(Order order) {
        float total = 0f;
        
        switch (order.getPizzaName()) {
            case "bella", "marinara", "spianatacalabrese":
                total += 30;
                break;
            case "margherita":
                total += 22;
                break;
            case "trioformaggio":
                total += 25;
                break;
        }

        switch (order.getSize()) {
            case "sm":
                total *= 1;
                break;
            case "md":
                total *= 1.2;
                break;
            case "lg":
                total *= 1.5;
                break;
        }

        total *= order.getQuantity();
        if (order.getRush()) {
            total += 2;
        }
        order.setTotalCost(total);
        return total;
    }
    
    public List<ObjectError> validatePizzaOrder(Pizza pizza) {
        List<ObjectError> errors = new LinkedList<>();
        FieldError error;

        if (!pizzaNames.contains(pizza.getPizza().toLowerCase())) {
            error = new FieldError("pizza", "pizza", "We do not have the %s pizza".formatted(pizza.getPizza()));
            errors.add(error);
        }
        if (!pizzaSizes.contains(pizza.getSize().toLowerCase())) {
            error = new FieldError("pizza", "size", "We do not have the %s pizza size".formatted(pizza.getSize()));
            errors.add(error);
        }

        return errors;
    }

    public Optional<Order> getOrderDetails(String orderId) {
        String url = UriComponentsBuilder.fromUriString(restPizzaUrl + orderId).toUriString();
        RequestEntity<Void> request = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(request, String.class);
        Order order = Order.create(response.getBody());
        if (order == null) {
            return Optional.empty();
        }
        return Optional.of(order);
    }
}
