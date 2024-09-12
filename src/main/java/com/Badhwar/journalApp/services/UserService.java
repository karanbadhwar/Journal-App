package com.Badhwar.journalApp.services;

import com.Badhwar.journalApp.entity.JournalEntry;
import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user) {
        userRepository.save(user);
    }

    //Saving Encoded User Password.
    public boolean saveNewUser(User user) {
        try
        {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch(Exception e)
        {
            return false;
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void deleteByUserName(String userName) {
        userRepository.deleteByUserName(userName);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

}
