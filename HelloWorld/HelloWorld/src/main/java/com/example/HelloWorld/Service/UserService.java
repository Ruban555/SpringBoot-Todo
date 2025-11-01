package com.example.HelloWorld.Service;


import com.example.HelloWorld.Repository.UserRepository;
import com.example.HelloWorld.models.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(User user){
        return userRepository.save(user);
    }
}
