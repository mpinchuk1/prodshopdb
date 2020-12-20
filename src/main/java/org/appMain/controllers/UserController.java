package org.appMain.controllers;

import org.appMain.entities.CustomUser;
import org.appMain.security.CustomUserDetails;
import org.appMain.services.UserService;
import org.appMain.utils.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/getAllUsers")
    public List<CustomUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/newuser")
    public ResponseEntity<Void> newuser(@RequestParam(required = false) String firstName,
                                        @RequestParam(required = false) String lastName,
                                        @RequestParam String login,
                                        @RequestParam String password,
                                        @RequestParam(required = false) String email) {
        logger.info("handling register user request: " + login);
        String passHash = passwordEncoder.encode(password);

        if (userService.addUser(firstName, lastName, login, passHash, email,
                UserRole.USER)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCurUser")
    public CustomUser getCurUserLogin() {
        CustomUserDetails tempUser = getCurrentUser();
        return tempUser.getUser();
    }

    @RequestMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // !!!
    public ModelAndView admin(Model model) {
        //model.addAttribute("users", userService.getAllUsers());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @RequestMapping("/unauthorized")
    public ModelAndView unauthorized(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("unauthorized");
        return modelAndView;
    }

    private CustomUserDetails getCurrentUser() {
        //logger.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean isAdmin(User user) {
        Collection<GrantedAuthority> roles = user.getAuthorities();

        for (GrantedAuthority auth : roles) {
            if ("ROLE_ADMIN".equals(auth.getAuthority()))
                return true;
        }
        return false;
    }
}
