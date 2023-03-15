package ibf2022.ssf.day18.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2022.ssf.day18.model.Order;
import ibf2022.ssf.day18.model.Pizza;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/")
public class PizzaController {
    
    @GetMapping(path = {"/", "/index.html"})
    public String getIndex(Model model, HttpSession session) {
        session.invalidate();
        model.addAttribute("pizza", new Pizza());
        return "index";
    }

    @PostMapping(path = "/pizza")
    public String orderPizza(Model model, HttpSession session, @Valid Order order, BindingResult binding) {
        if (binding.hasErrors()) {
            return "index";
        }
        return "order";
    }
}
