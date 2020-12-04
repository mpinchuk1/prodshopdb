package org.appMain.controllers;

import org.appMain.entities.dto.UserDTO;
import org.appMain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customers")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public @ResponseBody
    UserDTO getAllUsers() {
        UserDTO userDTO = new UserDTO();
        userDTO.setCustomers(userService.getAllUsers());
        return userDTO;
    }
}
