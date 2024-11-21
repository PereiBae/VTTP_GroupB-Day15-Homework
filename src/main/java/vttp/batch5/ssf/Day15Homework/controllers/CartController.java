package vttp.batch5.ssf.Day15Homework.controllers;

import jakarta.servlet.http.HttpSession;
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
    public String viewCarts(HttpSession session, Model model) {
        // Check if a user is logged in
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Redirect to login if no user is logged in
            return "redirect:/";
        }

        // Retrieve the user and their carts
        User user = userService.getOrCreateUser(loggedInUser);
        model.addAttribute("user", user);
        model.addAttribute("carts", cartService.getCartsForUser(user));

        return "cart";
    }

    // Create a new cart
    @PostMapping("/carts/create")
    public String createCart(HttpSession session) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/";
        }
        User user = userService.getOrCreateUser(loggedInUser);
        cartService.createCartForUser(user);
        return "redirect:/carts";
    }

    // View a specific cart by ID
    @GetMapping("/cart/{cartId}")
    public String viewCart(@PathVariable("cartId") String cartId, HttpSession session, Model model) {
        // Check if a user is logged in
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Redirect to login if no user is logged in
            return "redirect:/";
        }

        // Retrieve the user and ensure they own the cart
        User user = userService.getOrCreateUser(loggedInUser);
        if (!user.getCartIds().contains(cartId)) {
            throw new IllegalArgumentException("Access denied: You do not own this cart.");
        }

        // Fetch the cart and pass data to the view
        Cart cart = cartService.getCartById(cartId);
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
