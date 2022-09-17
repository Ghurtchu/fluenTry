package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

public class CatchableConsumableWithRunnables {

    @Test
    public void failAndThrowRuntimeException() {
        try {
            Try.evaluate(() -> { throw new ArithmeticException(); })
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
            Object object = Try.evaluate(() -> {})
                    .ifThrowsThenRunTaskOrElseGet(err -> {
                        System.out.println("Could not divide 10 by 2");
                        throw new RuntimeException("boom");
                    }, ArithmeticException.class);
            Assert.assertTrue(object != null);
        } catch (RuntimeException re) {
            Assert.fail();
        }
    }

}