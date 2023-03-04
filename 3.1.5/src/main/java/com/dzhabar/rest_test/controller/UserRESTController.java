package com.dzhabar.rest_test.controller;

import com.dzhabar.rest_test.model.User;
import com.dzhabar.rest_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class UserRESTController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserRESTController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/user")
    public ResponseEntity<User> showAuthUser() {
        return new ResponseEntity<> (userService.getCurrentUser(), HttpStatus.OK);
    }
}
