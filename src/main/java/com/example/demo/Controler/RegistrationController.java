package com.example.demo.Controler;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Model.Role;
import com.example.demo.Reprository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/register")
    public String registration() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(UserEntity user, Map<String, Object> model) {
        UserEntity userFromDb = userRepo.findByUsername(user.getUsername());

        System.out.println(1);
        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "register";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        System.out.println(user);
        System.out.println(11);
        userRepo.save(user);

        return "redirect:/login";
    }
}