package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.RoleService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final StateRepository stateRepository;
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository, StateRepository stateRepository) {
        this.roleRepository = roleRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public Role create(Role role) {
            return Optional
                    .ofNullable(role)
                    .map(roleRepository::save)
                    .orElseThrow(() -> new NullEntityReferenceException("Role can't be null and won't be affected!"));
    }

    @Override
    public Role readById(long id) {
            return Optional
                    .of(id)
                    .flatMap(roleRepository::findById)
                    .orElseThrow(() -> new EntityNotFoundException("Role with id " + id + " not found"));
    }

    @Override
    public Role update(Role role) {
        return Optional
                .ofNullable(role)
                .map(roleRepository::save)
                .orElseThrow(() -> new NullEntityReferenceException("Role can't be null and won't be updated"));
    }

    private Role updateFields(Role oldRole, Role updatedRole) {
        return null;
    }

    @Override
    public void delete(long id) {
        Optional
                .of(id)
                .filter(roleRepository::existsById)
                .map(roleRepository::removeById)
                .orElseThrow(() -> new EntityNotFoundException("Role with id " + id + " not found"));
    }

    @Override
    public List<Role> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.isEmpty() ? new ArrayList<>() : roles;
    }
}
