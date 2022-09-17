package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

public class CatchableConsumableWithConsumersTest {

    @Test
    public void failAndThrowRuntimeException() {
        try {
            Try.evaluate(42, (Integer i) -> { throw new ArithmeticException(); })
                    .ifThrowsThenRunTaskOrElseGet(err -> {
                        System.out.println("Something went wrong");
                        throw new RuntimeException("boom");
                    }, ArithmeticException.class);
        } catch (RuntimeException re) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void succeedWithValue() {
        try {
            Object object = Try.evaluate(42, (Integer i) -> {})
                    .ifThrowsThenRunTaskOrElseGet(err -> {
                        System.out.println("boom " + err.getMessage());
                        throw new RuntimeException("boom");
                    }, ArithmeticException.class);
            Assert.assertTrue(object != null);
        } catch (RuntimeException re) {
            Assert.fail();
        }
    }
}
