package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.ToDoService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    private ToDoRepository todoRepository;

    public ToDoServiceImpl(ToDoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public ToDo create(ToDo todo) {
        return Optional
                .ofNullable(todo)
                .map(todoRepository::save)
                .orElseThrow(() -> new NullEntityReferenceException("ToDo can't be null and won't be affected!"));
    }

    @Override
    public ToDo readById(long id) {
        return Optional
                .of(id)
                .flatMap(todoRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("ToDo with id " + id + " not found"));
    }

    @Override
    public ToDo update(ToDo todo) {
            ToDo oldTodo = readById(todo.getId());
                return todoRepository.save(todo);
    }

    @Override
    public void delete(long id) {
        Optional
                .of(id)
                .filter(todoRepository::existsById)
                .map(todoRepository::removeById)
                .orElseThrow(() -> new EntityNotFoundException("ToDo with id " + id + " not found"));
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
