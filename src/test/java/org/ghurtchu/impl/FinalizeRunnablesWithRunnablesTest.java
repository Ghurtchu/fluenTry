package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class FinalizeRunnablesWithRunnablesTest {

    @Test
    public void finalizeWithFailureRunnable() {
        AtomicBoolean caughtException = new AtomicBoolean(false);
        Try.of(() -> { throw new RuntimeException(); })
                .endWith(
                        () -> System.out.println("Success!"),
                        () -> {
                            System.out.println("Failure :(");
                            caughtException.set(true);
                        });
        Assert.assertTrue(caughtException.get());
    }

    @Test
    public void finalizeWithSuccessRunnable() {
        AtomicBoolean caughtException = new AtomicBoolean(false);
        Try.of(() -> {})
                .endWith(
                        () -> {
                            System.out.println("Success!");
                            caughtException.set(true);
                        },
                        () -> System.out.println("Failure :("));
        Assert.assertTrue(caughtException.get());
    }

}
