package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

public class PureCatchableMappableForRunnablesTest {

    @Test
    public void getDefault() {
        int result = Try.run(() -> { throw new ArithmeticException(); })
                .ifThrowsThenGetDefaultOrElseMap(
                        obj -> 1,
                        0,
                        ArithmeticException.class);
        Assert.assertEquals(0, result);
    }

    @Test
    public void getOne() {
        int result = Try.run(() -> {})
                .ifThrowsThenGetDefaultOrElseMap(
                        obj -> 1,
                        0,
                        ArithmeticException.class);
        Assert.assertEquals(1, result);
    }

}
