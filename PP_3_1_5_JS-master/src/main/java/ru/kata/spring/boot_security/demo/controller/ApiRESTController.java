package ru.kata.spring.boot_security.demo.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.servise.RoleService;
import ru.kata.spring.boot_security.demo.servise.UserService;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiRESTController {
    private final UserService userService;
    private final RoleService roleService;

    public ApiRESTController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/user")
    public ResponseEntity<UserForView> authenticatedUser(Principal principal) {
        User user = userService.findByName(principal.getName());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        UserForView user1 = new UserForView(user.getId(), user.getUsername(), user.getPassword(), user.getLastName(), (Collection<? extends MyGrantedAuthority>) authorities);
        user1.setId(user.getId());
        System.out.println(user1.getId() + " get");
        return ResponseEntity.ok(user1);
    }

    @RequestMapping("/users")
    public ResponseEntity<List<UserForView>> usersList() {
        List<UserForView> response = new ArrayList<>();
        userService.findAll().forEach(user -> {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            UserForView user1 = new UserForView(user.getId(), user.getUsername(), user.getPassword(), user.getLastName(), (Collection<? extends MyGrantedAuthority>) authorities);
            user1.setId(user.getId());
            response.add(user1);
        });
        return ResponseEntity.ok(response);
    }

    @RequestMapping("/user/{id}")
    public ResponseEntity<UserForView> showUser(@PathVariable("id") long id) {
        User user = userService.findById(id);
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        UserForView user1 = new UserForView(user.getId(), user.getUsername(), user.getPassword(), user.getLastName(), (Collection<? extends MyGrantedAuthority>) authorities);
        user1.setId(user.getId());
        return ResponseEntity.ok(user1);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newUser(@RequestBody UserForView user) {
        try {
            User userWithRoles = new User(user.password, user.username, user.lastName);
            UserForView.authorities.forEach(from -> {
                roleService.findAll().forEach(role -> {
                    if (role.getAuthority().equals(from.getAuthority())) {
                        userWithRoles.setRole(role);
                    }
                });
            });
            userService.save(userWithRoles);
            return ResponseEntity.ok(true);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(false);
        }
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> editUser(@RequestBody UserForView user) {
        try {
            System.out.println(user.id + " " + user.username + " " + user.password + " " + user.lastName);
            User userWithRoles = userService.findById(user.id);
            userWithRoles.setPassword(user.password);
            userWithRoles.setUsername(user.username);
            userWithRoles.setLastName(user.lastName);
            UserForView.authorities.forEach(from -> {
                roleService.findAll().forEach(role -> {
                    if (role.getAuthority().equals(from.getAuthority())) {
                        userWithRoles.setRole(role);
                    }
                });
            });
            userService.save(userWithRoles);
            return ResponseEntity.ok(true);
        } catch (RuntimeException e) {
            System.out.println("error");
            return ResponseEntity.ok(false);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        System.out.println(id + " delete");
        userService.delete(id);
    }

    private static class UserForView {
        private long id;
        private String username;
        private String password;
        private String lastName;
        private static Collection<? extends MyGrantedAuthority> authorities;

        public UserForView() {
        }

        public UserForView(String username, String password, String lastName, Collection<? extends MyGrantedAuthority> authorities) {
            this.username = username;
            this.password = password;
            this.lastName = lastName;
            UserForView.authorities = authorities;
        }

        public UserForView(long id, String username, String password, String lastName, Collection<? extends MyGrantedAuthority> authorities) {
            this(username, password, lastName, authorities);
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(Collection<? extends MyGrantedAuthority> authorities) {
            this.authorities = authorities;
        }
    }

    public static class MyGrantedAuthority implements GrantedAuthority {
        private String authority;

        public MyGrantedAuthority() {
        }

        public void setAuthority(String string) {
            this.authority = string;
        }

        @Override
        public String getAuthority() {
            return authority;
        }
    }
}
