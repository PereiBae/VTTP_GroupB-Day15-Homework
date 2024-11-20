package vttp.batch5.ssf.Day15Homework.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    @NotBlank(message = "Cart ID cannot be blank")
    private String cartId;

    private Map<String, Integer> items = new HashMap<>();

    @Positive(message = "Total quantity must be positive")
    private int totalQuantity;

    // Constructors
    public Cart() {}

    public Cart(String cartId) {
        this.cartId = cartId;
    }

    // Getters and Setters
    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    // Helper method to add or update an item
    public void addItem(String itemName, int quantity) {
        // Merge the new quantity with the existing one for the same item
        this.items.merge(itemName, quantity, Integer::sum);
        // Update the total quantity
        this.totalQuantity += quantity;
    }

}
