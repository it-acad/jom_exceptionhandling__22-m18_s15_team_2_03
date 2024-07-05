package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository todoRepository;


    @Override
    public ToDo create(ToDo todo) {
            return Optional.ofNullable(todo)
                    .map(todoRepository::save)
                    .orElseThrow(() -> new EntityNotFoundException("Wrong ToDo format or null"));
    }

    @Override
    public ToDo readById(long id) {
        Optional<ToDo> optional = todoRepository.findById(id);
            return optional.orElseThrow(() -> new EntityNotFoundException("to do not found"));
    }

    @Override
    public ToDo update(ToDo todo) {

            Optional<ToDo> newTodo = Optional.ofNullable(todo);
            Optional<ToDo> oldTodo = Optional.ofNullable(readById(todo.getId()));

            if (oldTodo.isPresent() && newTodo.isPresent()) {
                return todoRepository.saveAndFlush(todo);
            } else {
                throw new EntityNotFoundException("Wrong ToDo format or null");
            }
    }

    @Override
    public void delete(long id) {
        ToDo todo = readById(id);
            todoRepository.delete(todo);
    }

    @Override
    public List<ToDo> getAll() {
        List<ToDo> todos = todoRepository.findAll();
        return todos.isEmpty() ? new ArrayList<>() : todos;
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        List<ToDo> todos = todoRepository.getByUserId(userId);
        return todos.isEmpty() ? new ArrayList<>() : todos;
    }
}
