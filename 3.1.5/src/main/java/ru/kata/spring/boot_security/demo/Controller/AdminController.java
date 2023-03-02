package ru.kata.spring.boot_security.demo.Controller;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Entities.User;
import ru.kata.spring.boot_security.demo.Service.RoleService;
import ru.kata.spring.boot_security.demo.Service.UserService;
import ru.kata.spring.boot_security.demo.Util.UserValidator;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserDetailsService userDetailsService;
    private final RoleService roleService;
    public AdminController(UserDetailsService userDetailsService, RoleService roleService) {
        this.userDetailsService = userDetailsService;
        this.roleService = roleService;
    }
    @GetMapping()
    public String index(Principal principal, Model model) {
        model.addAttribute("myUser", userDetailsService.loadUserByUsername(principal.getName()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/index";
    }
}
