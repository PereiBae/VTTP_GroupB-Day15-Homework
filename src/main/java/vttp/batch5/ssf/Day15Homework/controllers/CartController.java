package vttp.batch5.ssf.Day15Homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vttp.batch5.ssf.Day15Homework.models.Cart;
import vttp.batch5.ssf.Day15Homework.models.User;
import vttp.batch5.ssf.Day15Homework.services.CartService;
import vttp.batch5.ssf.Day15Homework.services.UserService;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    // Display the list of carts for the user
    @GetMapping("/carts")
    public String viewCarts(@RequestParam("name") String name, Model model) {
        // Get the user
        User user = userService.getOrCreateUser(name);

        // Get the list of carts for the user
        model.addAttribute("user", user);
        model.addAttribute("carts", cartService.getCartsForUser(user));

        return "cart"; // Thymeleaf template for displaying carts
    }

    // Create a new cart
    @PostMapping("/carts/create")
    public String createCart(@RequestParam("name") String name) {
        // Get the user and create a new cart for them
        User user = userService.getOrCreateUser(name);
        cartService.createCartForUser(user);

        // Redirect back to the carts page
        return "redirect:/carts?name=" + name;
    }

    // View a specific cart by ID
    @GetMapping("/cart/{cartId}")
    public String viewCart(@PathVariable("cartId") String cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart with ID " + cartId + " not found.");
        }
        // Retrieve the user from the cart's association
        User user = userService.getUserByCartId(cartId);
        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        return "CartID";
    }

    // Add an item to the cart
    @PostMapping("/cart/{cartId}/add")
    public String addItemToCart(
            @PathVariable("cartId") String cartId,
            @RequestParam("itemName") String itemName,
            @RequestParam("quantity") int quantity) {
        cartService.addItemToCart(cartId, itemName, quantity);
        return "redirect:/cart/" + cartId;
    }

}
