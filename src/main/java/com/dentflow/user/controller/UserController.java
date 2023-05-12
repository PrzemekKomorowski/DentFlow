package com.dentflow.user.controller;

import com.dentflow.auth.model.AuthUserResponse;
import com.dentflow.exception.ApiRequestException;
import com.dentflow.user.model.UserRequest;
import com.dentflow.user.model.UserResponse;
import com.dentflow.user.model.User;
import com.dentflow.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Set<String> getAllUsersEmails(Authentication authentication) {
        if(authentication == null) {
            throw new ApiRequestException("Your token has expired");
        }
        User user = (User) authentication.getPrincipal();
        return userService.getAllEmails(user.getEmail());
    }

    @GetMapping("/profile")
    public UserResponse getUserProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return new UserResponse(user.getFirstName(), user.getLastName(), user.getEmail());
    }

    @PatchMapping("/profile")
    public void updateProfile(@RequestBody UserRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.updateUser(user.getEmail(), request);
    }

    @GetMapping("/getUser")
    public ResponseEntity<AuthUserResponse> getCurrentUser(Authentication authentication){
        if(authentication == null) {
            throw new ApiRequestException("Your token has expired");
        }

        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new AuthUserResponse(user.getEmail(),user.getRoles()));
    }
}