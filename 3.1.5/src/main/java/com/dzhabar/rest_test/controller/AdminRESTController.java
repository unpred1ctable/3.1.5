package com.dzhabar.rest_test.controller;

import com.burtsev.rest_test.model.*;
import com.burtsev.rest_test.service.*;
import com.dzhabar.rest_test.model.User;
import com.dzhabar.rest_test.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admins")
public class AdminRESTController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminRESTController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping
    public ResponseEntity<List<User>> showUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> showUser(@PathVariable("id") int id) {
        return new ResponseEntity<> (userService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/userAuth")
    public ResponseEntity<User> showAuthUser() {
        return new ResponseEntity<> (userService.getCurrentUser(), HttpStatus.OK);
    }

    @PostMapping("/newAddUser")
    public ResponseEntity<HttpStatus> saveNewUser(
            @RequestBody User user
    ) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);
        return new ResponseEntity<> (HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return new ResponseEntity<> (HttpStatus.OK);
    }


    @PatchMapping("/users/{id}")
    public ResponseEntity<HttpStatus> userSaveEdit(@RequestBody @NotNull User user, @PathVariable Integer id) {
        user.setId(id);
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user, id);

        return new ResponseEntity<> (HttpStatus.OK);
    }
}
