package com.Badhwar.journalApp.controller;

import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @PostMapping
    public void createUser(@RequestBody User user)
    {
        userService.saveNewUser(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName)
    {
        User userInDb = userService.findByUserName(userName);
        if(userInDb != null)
        {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveUser(userInDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
