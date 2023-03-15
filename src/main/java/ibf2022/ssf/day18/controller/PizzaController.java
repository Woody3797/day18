package ibf2022.ssf.day18.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ibf2022.ssf.day18.model.Delivery;
import ibf2022.ssf.day18.model.Order;
import ibf2022.ssf.day18.model.Pizza;
import ibf2022.ssf.day18.service.PizzaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PizzaController {
    
    @Autowired
    private PizzaService pizzaService;

    @GetMapping(path = {"/", "/index.html"})
    public String getIndex(Model model, HttpSession session) {
        session.invalidate();
        model.addAttribute("pizza", new Pizza());
        return "index";
    }

    @PostMapping(path = "/pizza")
    public String postPizza(Model model, HttpSession session, @Valid Pizza pizza, BindingResult bindings) {
        if (bindings.hasErrors()) {
            return "index";
        }

        List<ObjectError> errors = pizzaService.validatePizzaOrder(pizza);
        if (!errors.isEmpty()) {
            for (ObjectError error : errors) {
                bindings.addError(error);
            }
            return "index";
        }
        session.setAttribute("pizza", pizza);
        model.addAttribute("delivery", new Delivery());
        return "delivery";
    }

    @PostMapping(path = "/pizza/order")
    public String postPizzaOrder(Model model, HttpSession session, @Valid Delivery delivery, BindingResult bindings) {
        if (bindings.hasErrors()) {
            return "delivery";
        }

        Pizza pizza = (Pizza) session.getAttribute("pizza");
        Order order = pizzaService.savePizzaOrder(pizza, delivery);
        model.addAttribute("order", order);
        return "order";
    }

    @GetMapping(path = "/pizza/details/{orderId}")
    public String getOrderDetails(Model model, @PathVariable String orderId) {
        Optional<Order> order = pizzaService.getOrderDetails(orderId);
        model.addAttribute("order", order.get());
        return "order";
    }
}
