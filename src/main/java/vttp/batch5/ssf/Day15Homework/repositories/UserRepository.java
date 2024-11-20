package vttp.batch5.ssf.Day15Homework.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vttp.batch5.ssf.Day15Homework.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private static final String HASH_KEY = "User";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Find a user by name
    public User findByName(String name) {
        return (User) redisTemplate.opsForHash().get(HASH_KEY, name);
    }

    // Save a user to Redis
    public void save(User user) {
        redisTemplate.opsForHash().put(HASH_KEY, user.getName(), user);
    }

    public List<User> findAll() {
        return redisTemplate.opsForHash().values(HASH_KEY)
                .stream()
                .map(obj -> (User) obj)
                .collect(Collectors.toList());
    }

}
