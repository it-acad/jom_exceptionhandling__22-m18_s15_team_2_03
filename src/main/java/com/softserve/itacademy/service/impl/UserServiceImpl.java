package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User create(User user) {
            return Optional.ofNullable(user)
                    .map(userRepository::save)
                    .orElseThrow(() -> new EntityNotFoundException("User wrong format or null"));
    }

    @Override
    public User readById(long id) {
        Optional<User> optional = userRepository.findById(id);
            return optional.orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public User update(User user) {

            Optional<User> newUser = Optional.ofNullable(user);
            Optional<User> oldUser = userRepository.findById(user.getId());

            if (oldUser.isPresent() && newUser.isPresent()) {
                return userRepository.save(user);
            } else {
                throw new EntityNotFoundException("Wrong user format or null");
            }
    }

    @Override
    public void delete(long id) {
        User user = readById(id);
            userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

}
