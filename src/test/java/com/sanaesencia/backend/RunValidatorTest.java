package com.sanaesencia.backend;

import com.sanaesencia.backend.util.RunValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunValidatorTest {

    @Test
    void testAdminValidationAndNormalization() {
        assertTrue(RunValidator.isValidRun("admin"));
        assertTrue(RunValidator.isValidRun("ADMIN"));
        assertEquals("admin", RunValidator.normalizeRun("admin"));
        assertEquals("admin", RunValidator.normalizeRun("ADMIN"));
    }

    @Test
    void testChileanRunValidation() {
        assertTrue(RunValidator.isValidRun("11.111.111-1"));
        assertTrue(RunValidator.isValidRun("11111111-1"));
        assertTrue(RunValidator.isValidRun("20.000.001-3"));
        assertTrue(RunValidator.isValidRun("20000001-3"));
        assertTrue(RunValidator.isValidRun("20.000.100-1"));
        assertTrue(RunValidator.isValidRun("20000100-1"));

        assertEquals("11111111-1", RunValidator.normalizeRun("111111111"));
        assertEquals("20000001-3", RunValidator.normalizeRun("200000013"));
    }
}
