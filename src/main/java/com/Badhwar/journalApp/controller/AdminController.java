package com.Badhwar.journalApp.controller;

import com.Badhwar.journalApp.Cache.AppCache;
import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        if(allUsers != null & !allUsers.isEmpty())
        {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdmin(@RequestBody User user)
    {
        userService.saveAdmin(user);
    }

    //Utility Method
    @GetMapping("/clear-app-cache")
    public void ClearAppCache()
    {
        appCache.init();
    }

}
