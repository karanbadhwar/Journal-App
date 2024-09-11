package com.Badhwar.journalApp.controller;

import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check") //GET Call
    public String healthCheck()
    {
        return "OK";
    }
@PostMapping("/create-user")
public void createUser(@RequestBody User user)
{
    userService.saveNewUser(user);
}
}
