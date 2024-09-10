package com.Badhwar.journalApp.controller;

import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //When the User gets Authenticated, SecurityContextHolder gets the User's Credentials

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        if(userInDb != null)
        {
            userInDb.setUserName(user.getUserName());
            if(!passwordEncoder.matches( user.getPassword(),userInDb.getPassword()))
            {
                userInDb.setPassword(user.getPassword());
                userService.saveNewUser(userInDb);
                System.out.println("Password Changed as well");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            userService.saveUser(userInDb);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody User user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
