package com.Badhwar.journalApp.Repository;

import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.repository.UserRespositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRespositoryImplTest {

    @Autowired
    private UserRespositoryImpl userRespository;

    @Test
    public void testGetUserforSA()
    {
        List<User> userforSA = userRespository.getUserforSA();
        assertNotNull(userforSA);

    }
}
