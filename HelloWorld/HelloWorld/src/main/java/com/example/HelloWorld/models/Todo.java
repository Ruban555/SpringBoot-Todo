package com.example.HelloWorld.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue
    Long id;
    @NonNull
    @Size(min = 6)
    String title;
    String description;
    Boolean completed;


}
