package com.example.HelloWorld.Repository;

import com.example.HelloWorld.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TodoRepository extends JpaRepository<Todo,Long> {

}
