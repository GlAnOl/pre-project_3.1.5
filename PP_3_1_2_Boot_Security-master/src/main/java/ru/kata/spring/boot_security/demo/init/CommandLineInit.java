package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineInit implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public CommandLineInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @Override
    public void run(String... args) {

        Role roleUser = new Role(1, "ROLE_USER");
        Role roleAdmin = new Role(2, "ROLE_ADMIN");
        roleService.addRole(roleUser);
        roleService.addRole(roleAdmin);


        userService.saveUser(new User("admin", "a", "adminovich", 33, "admin@mail.com"),
                new HashSet<Role>(Set.of(roleAdmin, roleUser)));
        userService.saveUser(new User("user", "u", "usereovich", 22, "user@mail.com"),
                new HashSet<Role>(Set.of(roleUser)));


    }
}
