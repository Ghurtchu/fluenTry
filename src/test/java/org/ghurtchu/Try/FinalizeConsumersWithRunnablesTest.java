package org.ghurtchu.Try;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class FinalizeConsumersWithRunnablesTest {

    @Test
    public void finalizeWithFailureRunnable() {
        AtomicBoolean caughtException = new AtomicBoolean(false);
        Try.of(42, (Integer i) -> {
                    System.out.println("got " + i);
                    throw new RuntimeException();
                })
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
        Try.of(42, (Integer i) -> {
                    System.out.println("got " + i);
                })
                .endWith(
                        () -> {
                            System.out.println("Success!");
                            caughtException.set(true);
                        },
                        () -> System.out.println("Failure :("));
        Assert.assertTrue(caughtException.get());
    }

}
