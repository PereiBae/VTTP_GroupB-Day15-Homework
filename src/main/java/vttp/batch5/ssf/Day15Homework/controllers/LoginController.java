package vttp.batch5.ssf.Day15Homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vttp.batch5.ssf.Day15Homework.models.User;
import vttp.batch5.ssf.Day15Homework.services.UserService;

@Controller
@RequestMapping(path = {"/" , "/index.html"})
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showLoginPage(Model model) {
        return "index";
    }

    // Handle login
    @PostMapping("/login")
    public String loginUser(@RequestParam("name") String name, Model model) {
        // Check if user exists or create a new one
        User user = userService.getOrCreateUser(name);

        // Add user information to the model
        model.addAttribute("user", user);

        // Redirect to the carts page
        return "redirect:/carts?name=" + name;
    }
}

