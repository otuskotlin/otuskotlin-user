package ru.otus.otuskotlin.user.transport.multiplatform.models;

import org.junit.Test;
import ru.otus.otuskotlin.common.validators.HandleError;
import ru.otus.otuskotlin.common.validators.ValidationResult;
import ru.otus.otuskotlin.common.validators.fields.ValidatorId;

import static org.junit.Assert.*;

public class KmpUserCreateTest {
    @Test
    public void validationResultTest() {
        ValidationResult result = new ValidationResult(
                new HandleError(
                        "code",
                        "group",
                        HandleError.Level.ERROR,
                        "someField",
                        "Some Error Message",
                        "Some description for the error"
                ),
                new HandleError()
        );
        assertFalse(result.isOk());
    }

    @Test
    public void handleErrorDefaultsTest() {
        HandleError error = new HandleError(
                        "code"
                        // Перечислять все аргументы не требуется с аннотацией @JvmOverloads
//                        "group",
//                        HandleError.Level.ERROR,
//                        "someField",
//                        "Some Error Message",
//                        "Some description for the error"
        );
        assertEquals("code", error.getCode());
    }

    @Test
    public void validatorIdTest() {
        ValidatorId validator = new ValidatorId("id");
        ValidationResult result = validator.validate("123HHH&*");
        assertFalse(result.isOk());
    }

    @Test
    public void companionTest() {
        ValidationResult result = ValidationResult.Companion.getSUCCESS();

        ValidationResult result1 = ValidationResult.getSUCCESS();
//        ValidationResult result1 = ValidationResult.SUCCESS;

        ValidationResult result2 = ValidationResult.OK;

        assertTrue(result.isOk());
        assertTrue(result1.isOk());
        assertTrue(result2.isOk());
    }
}
