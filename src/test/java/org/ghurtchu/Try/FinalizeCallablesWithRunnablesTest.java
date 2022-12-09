package org.ghurtchu.Try;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class FinalizeCallablesWithRunnablesTest {

    @Test
    public void finalizeWithFailureRunnable() {
        AtomicBoolean caughtException = new AtomicBoolean(false);
        Try.of(() -> 42 / 0)
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
        Try.of(() -> 42 / 7)
                .endWith(
                        () -> {
                            System.out.println("Success!");
                            caughtException.set(true);
                        },
                        () -> System.out.println("Failure :("));
        Assert.assertTrue(caughtException.get());
    }

}
