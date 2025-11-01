package com.example.HelloWorld.Service;

import com.example.HelloWorld.Repository.TodoRepository;
import com.example.HelloWorld.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public Todo printTodos(Todo todo){
        return todoRepository.save(todo);
    }

    public Todo getTodos(Long id){
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    public Page<Todo> getTodoPages(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return  todoRepository.findAll(pageable);
    }

    public List<Todo> getall(){
        return todoRepository.findAll();
    }

    public Todo updateTodo(Todo todo){
        return todoRepository.save(todo);
    }

    public void deleteTodo(long id){
         todoRepository.delete(getTodos(id));
    }
}
