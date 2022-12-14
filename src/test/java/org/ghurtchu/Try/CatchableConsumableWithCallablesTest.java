package org.ghurtchu.Try;

import org.junit.Assert;
import org.junit.Test;

public class CatchableConsumableWithCallablesTest {

    @Test
    public void failAndThrowRuntimeException() {
        try {
            Try.of(() -> 5 / 0)
                    .ifThrowsThenRunTaskOrElseGet(err -> {
                        System.out.println("Could not divide 5 by 0");
                        throw new RuntimeException("boom");
                    }, ArithmeticException.class);
        } catch (RuntimeException re) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void succeedWithValue() {
        try {
            int result = Try.of(() -> 10 / 2)
                    .ifThrowsThenRunTaskOrElseGet(err -> {
                        System.out.println("Could not divide 10 by 2");
                        throw new RuntimeException("boom");
                    }, ArithmeticException.class);
            Assert.assertEquals(5, result);
        } catch (RuntimeException re) {
            Assert.fail();
        }
    }

}
