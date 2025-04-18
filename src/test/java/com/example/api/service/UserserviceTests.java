package com.example.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.api.repository.UserEntryRepository;
@SpringBootTest
public class UserserviceTests {

    @Autowired
    private UserEntryRepository userEntryRepository;

    @Test
    @Disabled
    public void testFindByUserName(){
        // assertEquals(4, 2+1);
        assertNotNull(userEntryRepository.findByUserName("Meet"));
    }

    @ParameterizedTest
    @CsvSource({
        "1,1,2",
        "2,10,12",
        "3,3,9"
    })
    public void test(int a,int b,int c){
        assertEquals(c, a+b);
    }
}
