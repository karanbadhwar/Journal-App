package com.Badhwar.journalApp.services;

import com.Badhwar.journalApp.entity.User;
import com.Badhwar.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;

import static org.junit.jupiter.api.Assertions.*;

//We can write multiple Tests as Methods

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @Test
    public void testFindByUserName()
    {
//        assertEquals(4, 2+2);
//        assertNotNull(userRepository.findByUserName("Karan"));
        User user = userRepository.findByUserName("Karan");
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @Disabled
    @ParameterizedTest
//    @ValueSource(strings = {
//            "Karan",
//            "Badhwar Karan"
//    })
    @ArgumentsSource(userArgumentsProvider.class)
    public void testFindByUserNameWithParams(String userName)
    {
        assertNotNull(userRepository.findByUserName(userName), "Failed for: "+userName);
    }

    @ParameterizedTest
    @ArgumentsSource(userArgumentsProvider.class)
    public void testAddingUser(User user)
    {
        assertTrue(userService.saveNewUser(user));
    }


    @Disabled
    @ParameterizedTest
   @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    public void test(int a, int b, int expected)
    {
        assertEquals(expected, a + b);
    }
}
