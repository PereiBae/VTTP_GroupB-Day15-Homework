package vttp.batch5.ssf.Day15Homework.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class User {

    @NotBlank ( message = "Name cannot be blank")
    @Size(min = 2, max = 32, message = "Name cannot exceed 32 characters")
    private String name;

    private Set<String> cartIds = new HashSet<>();

    public User() {

    }

    public User(String name) {
        this.name = name;
    }

    public @NotBlank(message = "Name cannot be blank") @Size(min = 2, max = 32, message = "Name cannot exceed 32 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name cannot be blank") @Size(min = 2, max = 32, message = "Name cannot exceed 32 characters") String name) {
        this.name = name;
    }

    public Set<String> getCartIds() {
        return cartIds;
    }

    public void setCartIds(Set<String> cartIds) {
        this.cartIds = cartIds;
    }

    // Helper method to add a cart ID
    public void addCartId(String cartId) {
        this.cartIds.add(cartId);
    }
}
