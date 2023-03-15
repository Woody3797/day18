package ibf2022.ssf.day18.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.ssf.day18.model.Order;

@Repository
public class PizzaRepository {
    
    @Autowired
    @Qualifier("pizza")
    private RedisTemplate<String, String> template;

    public void save(Order order) {
        this.template.opsForValue().set(order.getOrderId(), order.toJSON().toString());
    }

    public Optional<Order> get(String orderId) {
        String json = template.opsForValue().get(orderId);
        if (json == null || json.trim().length() <= 0) {
            return Optional.empty();
        }
        return Optional.of(Order.create(json));
    }

}
