package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.domain.Team;
import com.example.bettingGame.core.dto.UserPostDto;
import com.example.bettingGame.core.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully saved"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public void createUser(@RequestBody UserPostDto userPostDto) {
        userService.createUser(userPostDto);
    }
}
