package com.example.userservice.shared.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class UtilsTest {

    @Autowired
    private Utils utils;

    @BeforeEach
    void setUp() throws Exception {
        String userId = utils.generatePublicId(30);
        assertNotNull(userId);
        assertTrue(userId.length() == 30);
    }

    @Test
    void testGeneratePublicId() {

    }
}