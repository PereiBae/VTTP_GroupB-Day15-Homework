package vttp.batch5.ssf.Day15Homework.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vttp.batch5.ssf.Day15Homework.models.Cart;
import vttp.batch5.ssf.Day15Homework.models.User;

@Repository
public class CartRepository {

    private static final String CART_HASH_KEY = "Cart";
    private static final String USER_HASH_KEY = "User";

    @Autowired
    private RedisTemplate <String, Object> redisTemplate;

    public Cart findById(String cartId) {
        return (Cart) redisTemplate.opsForHash().get(CART_HASH_KEY, cartId);
    }

    public void save(Cart cart) {
        redisTemplate.opsForHash().put(CART_HASH_KEY, cart.getCartId(), cart);
    }

    public void updateUser(User user) {
        redisTemplate.opsForHash().put(USER_HASH_KEY, user.getName(), user);
    }

}
