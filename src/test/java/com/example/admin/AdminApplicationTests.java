package com.example.admin;

import com.example.admin.entity.User;
import com.example.admin.repository.RoleRepository;
import com.example.admin.repository.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Locale;

@SpringBootTest
class AdminApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void faker() {
        jdbcTemplate.execute("TRUNCATE TABLE user");

        Faker faker = new Faker(new Locale("zh-CN"));
        String password = new BCryptPasswordEncoder().encode("123456");
        ArrayList<User> users = new ArrayList<>();

        User user = new User();
        user.setUsername("admin");
        user.setPassword(password);
        user.setCreatedBy(0L);
        users.add(user);

        for (int i = 0; i < 50; i++) {
            user = new User();
            user.setUsername(faker.name().fullName());
            user.setPassword(password);
            user.setCreatedBy(0L);
            users.add(user);
        }
        userRepository.saveAll(users);
    }
}
