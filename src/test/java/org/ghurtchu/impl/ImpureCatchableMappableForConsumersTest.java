package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImpureCatchableMappableForConsumersTest {

    @Test
    public void catchArithmeticExceptionAndThenThrowRuntimeException() {
        AtomicBoolean caught = new AtomicBoolean(false);
        try {
            Try.evaluate(42, (Integer i) -> {
                        System.out.println("got " + i);
                        throw new RuntimeException("boom");
                    })
                    .ifThrowsThenRunTask(err -> {
                        System.out.println("error occured " + err.getMessage());
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
            Try.evaluate(42, (Integer i) -> {
                        System.out.println("got " + i);
                    })
                    .ifThrowsThenRunTask(err -> {
                        System.out.println("Boom!");
                        caught.set(true);
                        throw new NullPointerException();
                    }, ArithmeticException.class, IllegalArgumentException.class, NoSuchElementException.class);
        } catch (NullPointerException ignored) {
            Assert.assertTrue(caught.get());
        }
    }

}
