package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImpureCatchableMappableForRunnables {

    @Test
    public void catchArithmeticExceptionAndThenThrowRuntimeException() {
        AtomicBoolean caught = new AtomicBoolean(false);
        try {
            Try.evaluate(() -> { throw new ArithmeticException(); })
                    .ifThrowsThenRunTask(err -> {
                        System.out.println("Could not divide!");
                        caught.set(true);
                        throw new RuntimeException();
                    }, ArithmeticException.class);
        } catch (RuntimeException ignored) {
            Assert.assertTrue(caught.get());
        }
    }

    @Test
    public void catchOneExceptionAndThenThrowNullPointerException() {
        AtomicBoolean caught = new AtomicBoolean(false);
        try {
            Try.evaluate(() -> { throw new NoSuchElementException(); })
                    .ifThrowsThenRunTask(err -> {
                        System.out.println("Boom! could not divide");
                        caught.set(true);
                        throw new NullPointerException();
                    }, ArithmeticException.class, IllegalArgumentException.class, NoSuchElementException.class);
        } catch (NullPointerException ignored) {
            Assert.assertTrue(caught.get());
        }
    }

}
