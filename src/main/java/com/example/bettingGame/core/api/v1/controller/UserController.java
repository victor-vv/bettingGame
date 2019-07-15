package com.example.bettingGame.core.api.v1.controller;

import com.example.bettingGame.core.domain.User;
import com.example.bettingGame.core.dto.UserDto;
import com.example.bettingGame.core.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getCurrentUser(@AuthenticationPrincipal User user) {
        return new UserDto(user.getId(), user.getUsername());
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successfully saved"),
//            @ApiResponse(code = 400, message = "Bad request")
//    })
//    public void createUser(@RequestBody UserDto userDto) {
//        userService.createUser(userDto);
//    }
}
