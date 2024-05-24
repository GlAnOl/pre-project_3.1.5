package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.util.UserNotCreatedException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }
    @Override
    @Transactional
    public boolean saveUser(User user) {

        //Здесь я проверяю есть ли роль у юзера, если ее нет, то добавляю "ROLE_USER"
        //иначе в БД можно добавить юзера без роли

        if (user.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        }
        user.setRoles(user.getRoles().stream()
                .map(role -> roleService.getByName(role.getRoleName()))
                .collect(Collectors.toSet()));

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    @Override
    public List<User> getListAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(int id){

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotCreatedException("Юзер не найден");
        }
        return user.orElse(new User());
    }

    @Override
    @Transactional
    public void updateUser(User user) {

        User userFromDB = userRepository.findUserById(user.getId());
        if (user.getRoles().isEmpty()) {
            user.setRoles(userFromDB.getRoles());
        } else {
            user.setRoles(user.getRoles().stream()
                    .map(role -> roleService.getByName(role.getRoleName()))
                    .collect(Collectors.toSet()));
        }
        if (!user.getPassword().equals(userFromDB.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        userRepository.save(user);
    }
    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}


