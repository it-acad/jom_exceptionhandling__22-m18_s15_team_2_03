package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.StateService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {
    private StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State create(State state) {
        return Optional
                .ofNullable(state)
                .map(stateRepository::save)
                .orElseThrow(() -> new NullEntityReferenceException("State can't be null and won't be affected!"));
    }

    @Override
    public State readById(long id) {
        return Optional
                .of(id)
                .flatMap(stateRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("State with id " + id + " not found"));
    }

    @Override
    public State update(State state) {
        return Optional
                .ofNullable(state)
                .map(stateRepository::save)
                .orElseThrow(() -> new NullEntityReferenceException("State can't be null and won't be updated"));
    }

    @Override
    public void delete(long id) {
        Optional
                .of(id)
                .filter(stateRepository::existsById)
                .map(stateRepository::removeById)
                .orElseThrow(() -> new EntityNotFoundException("State with id " + id + " not found"));
    }

    @Override
    public State getByName(String name) {
        Optional<State> optional = Optional.ofNullable(stateRepository.getByName(name));
            return optional.get();
    }

    @Override
    public List<State> getAll() {
        List<State> states = stateRepository.getAll();
        return states.isEmpty() ? new ArrayList<>() : states;
    }
}
