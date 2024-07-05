package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;


    @Override
    public Task create(Task user) {
            return taskRepository.save(user);
    }

    @Override
    public Task readById(long id) {

        return Optional
                .of(id)
                .flatMap(taskRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Task with id %s not found", id)));
    }

    @Override
    public Task update(Task task) {

         return Optional.ofNullable(task)
                 .map(taskRepository::save)
                 .orElseThrow(() -> new EntityNotFoundException(String.format("Task with id %s not found", task.getId())));
    }

    @Override
    public void delete(long id) {
        Optional<Task> task = Optional.ofNullable(readById(id));
        task.ifPresentOrElse(taskRepository::delete, () -> {
            throw new EntityNotFoundException(String.format("Task with id %s not found", task.get().getId()));
        });
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.isEmpty() ? new ArrayList<>() : tasks;
    }

    @Override
    public List<Task> getByTodoId(long todoId) {
        List<Task> tasks = taskRepository.getByTodoId(todoId);
        return tasks.isEmpty() ? new ArrayList<>() : tasks;
    }
}
