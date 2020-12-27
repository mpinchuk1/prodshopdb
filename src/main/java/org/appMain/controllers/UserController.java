package org.appMain.controllers;

import org.appMain.entities.dto.UserDTO;
import org.appMain.services.UserService;
import org.appMain.utils.UserAlreadyExistsException;
import org.appMain.utils.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid UserDTO userDto, BindingResult bindingResult) {
        logger.info("handling register user request: " + userDto.getLogin());

        if (bindingResult.hasErrors()) {
            return "register";
        }
        String password = userDto.getPassword();
        String passHash = passwordEncoder.encode(password);

        try {
            userService.addUser(userDto.getFirstName(), userDto.getLastName(),
                    userDto.getLogin(), passHash, userDto.getEmail(), UserRole.USER);
        } catch (UserAlreadyExistsException exception) {
            return "redirect:/register?error";
        }
        return "redirect:/register?success";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }
}
