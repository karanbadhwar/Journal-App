package com.Badhwar.journalApp.services;

import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import static org.mockito.Mockito.*;

//We removed all the Spring Context part, meaning removed all the connections with the DB and all, now Just using Mocks
//Mocking Dependencies, saves a lot of time and makes testing fast.

public class UserDetailsServiceImplTest {

    @InjectMocks // Now we are not creating it a Spring Context, rather than mock inject all dependencies
    private UserDetailsServicesImpl userDetailsServices;

    // To Inject Mock Bean rather than using actual Bean that extracts data from database, it saves time for Testing.
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUserNameTest()
    {
        //ArgumentMatchers.anyString() -> make sure any name is accepted otherwise, we can give Name as well like "Karan"..
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(User.builder()
                        .userName("Karan")
                        .password("abcdefg")
                        .roles(new ArrayList<>())
                        .build());
        UserDetails user = userDetailsServices.loadUserByUsername("Karan");
        Assertions.assertNotNull(user);
    }
}
