package com.dzhabar.rest_test.util;

import com.dzhabar.rest_test.model.Role;
import com.dzhabar.rest_test.model.User;
import com.dzhabar.rest_test.service.RoleService;
import com.dzhabar.rest_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class initDataBase {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public initDataBase(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        Role role1 = new Role("ROLE_USER");
        Role role2 = new Role("ROLE_ADMIN");

        roleService.add(role1);
        roleService.add(role2);

        Set<Role> roleAdmin = new HashSet<>();
        Set<Role> roleUser = new HashSet<>();
        roleUser.add(role1);
        roleAdmin.add(role2);

        User user = new User("100", "user@mail.ru", "user", "user", 20, roleUser);
        User admin = new User("100", "admin@mail.ru", "admin", "admin", 20, roleAdmin);

        userService.add(admin);
        userService.add(user);
    }
}
