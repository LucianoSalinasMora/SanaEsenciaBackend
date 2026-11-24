package com.sanaesencia.backend;

import com.sanaesencia.backend.repository.BoxRepository;
import com.sanaesencia.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OracleSpringBootContextTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoxRepository boxRepository;

    @Test
    void testOracleContextAndDataSeeding() {
        assertNotNull(userRepository);
        long userCount = userRepository.count();
        long boxCount = boxRepository.count();

        System.out.println("==================================================");
        System.out.println(">>> VERIFICACIÓN ORACLE AUTONOMOUS DATABASE:");
        System.out.println(">>> Total usuarios en Oracle: " + userCount);
        System.out.println(">>> Total boxes en Oracle: " + boxCount);
        System.out.println("==================================================");

        assertTrue(userCount > 0, "Los usuarios deben estar sembrados en Oracle");
        assertTrue(boxCount >= 8, "Los 8 boxes deben estar sembrados en Oracle");
    }
}
