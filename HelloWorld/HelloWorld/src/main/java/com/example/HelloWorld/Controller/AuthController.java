package com.example.HelloWorld.Controller;

import com.example.HelloWorld.Repository.UserRepository;
import com.example.HelloWorld.Service.UserService;
import com.example.HelloWorld.models.User;
import com.example.HelloWorld.utils.Jwtutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")

public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final Jwtutil jwtutil;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/Register")
    public ResponseEntity<String> Register(@RequestBody Map<String,String> body){
        String email = body.get("email");
        String password = body.get("password");
        String encodedPassword = passwordEncoder.encode(password);

        if(userRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("User already exist!",HttpStatus.CONFLICT);
        }
        userService.create(User.builder().email(email).password(encodedPassword).build());
        return new ResponseEntity<>("User creater successfully",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody Map<String,String> body){
        String  email = body.get("email");
        String password = body.get("password");

        var userOpt=userRepository.findByEmail(email);
        if(userOpt.isEmpty()){
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }

        User user=userOpt.get();
        if(!passwordEncoder.matches(password,user.getPassword())){
            return new ResponseEntity<>("Wrong password",HttpStatus.UNAUTHORIZED);
        }

        String token=jwtutil.generateToken(email);
        return ResponseEntity.ok(Map.of("token",token));
    }
}
