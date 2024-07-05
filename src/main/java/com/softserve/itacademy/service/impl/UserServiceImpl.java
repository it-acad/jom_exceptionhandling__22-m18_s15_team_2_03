package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return Optional
                .ofNullable(user)
                .map(userRepository::save)
                .orElseThrow(() -> new NullEntityReferenceException("User can't be null and won't be affected!"));
    }

    @Override
    public User readById(long id) {
            return Optional
                    .of(id)
                    .flatMap(userRepository::findById)
                    .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User update(User user) {
            User oldUser = readById(user.getId());
                return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        Optional
                .of(id)
                .filter(userRepository::existsById)
                .map(userRepository::removeById)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

}
