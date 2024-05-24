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


        User user = new User("user", "u", "usereovich", 22, "user@mail.com");
        User admin = new User("admin", "a", "adminovich", 33, "admin@mail.com");

        user.setRoles(  new HashSet<Role>(Set.of(roleUser)));
        admin.setRoles(new HashSet<Role>(Set.of(roleAdmin, roleUser)));

        userService.saveUser(user);
        userService.saveUser(admin);

    }
}
