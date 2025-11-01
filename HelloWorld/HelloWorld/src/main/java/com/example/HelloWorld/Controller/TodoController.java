package com.example.HelloWorld.Controller;

import com.example.HelloWorld.Service.TodoService;
import com.example.HelloWorld.models.Todo;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @PostMapping("/create")
    ResponseEntity<Todo> createTodo(@RequestBody Todo todo){
       return new ResponseEntity<>(todoService.printTodos(todo),HttpStatus.CREATED);
    }

    @ApiResponses(value={
            @ApiResponse(responseCode="200",description="Todo get succcessfully"),
            @ApiResponse(responseCode = "404",description = "Oops!! not now") 
    })
    @GetMapping("/{id}")
    ResponseEntity<Todo> getTodo(@PathVariable Long id){
        try{
            return new ResponseEntity<>(todoService.getTodos(id),HttpStatus.OK);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    ResponseEntity<List<Todo>> getall(){
        return new ResponseEntity<>(todoService.getall(),HttpStatus.OK);
    }

    @GetMapping("/pages")
    ResponseEntity<Page<Todo>> getTodoPage(@RequestParam int  page,@RequestParam int size){
        return new ResponseEntity<>(todoService.getTodoPages(page, size),HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<Todo>  updateTodo(@RequestBody Todo todo){
        return new ResponseEntity<>(todoService.updateTodo(todo),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    void deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
    }
}
