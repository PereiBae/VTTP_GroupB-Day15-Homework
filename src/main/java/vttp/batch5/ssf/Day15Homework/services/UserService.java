package vttp.batch5.ssf.Day15Homework.services;

import vttp.batch5.ssf.Day15Homework.models.User;
import vttp.batch5.ssf.Day15Homework.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getOrCreateUser(String name) {
        // Check if user already exists
        User user = userRepository.findByName(name);
        if (user == null) {
            // Create a new user if none exists
            user = new User(name);
            userRepository.save(user);
        }
        return user;
    }

    public User getUserByCartId(String cartId) {
        // Iterate through all users in Redis
        for (User user : userRepository.findAll()) {
            if (user.getCartIds().contains(cartId)) {
                return user; // Return the user if the cart ID is found
            }
        }
        throw new IllegalArgumentException("No user found for cart ID: " + cartId);
    }

}

