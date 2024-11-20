package vttp.batch5.ssf.Day15Homework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vttp.batch5.ssf.Day15Homework.models.Cart;
import vttp.batch5.ssf.Day15Homework.models.User;
import vttp.batch5.ssf.Day15Homework.repositories.CartRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getCartsForUser(User user) {
        List<Cart> carts = new ArrayList<>();
        for (String cartId : user.getCartIds()) {
            Cart cart = cartRepository.findById(cartId);
            if (cart != null) {
                carts.add(cart);
            }
        }
        return carts;
    }

    public void createCartForUser(User user) {
        // Generate a new cart ID
        String cartId = java.util.UUID.randomUUID().toString().substring(0, 8);

        // Create and save the new cart
        Cart cart = new Cart(cartId);
        cartRepository.save(cart);

        // Associate the cart with the user
        user.addCartId(cartId);
        cartRepository.updateUser(user); // Update the user in Redis with the new cart
    }

    public Cart getCartById(String cartId) {
        return cartRepository.findById(cartId);
    }

    public void addItemToCart(String cartId, String itemName, int quantity) {
        Cart cart = cartRepository.findById(cartId);
        if (cart != null) {
            cart.addItem(itemName, quantity);
            cartRepository.save(cart); // Save the updated cart back to Redis
        }
    }

}
