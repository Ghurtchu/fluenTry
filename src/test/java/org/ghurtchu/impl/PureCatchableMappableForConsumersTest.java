package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

public class PureCatchableMappableForConsumersTest {

    @Test
    public void getDefault() {
        int result = Try.evaluate(42, (Integer i) -> { throw new ArithmeticException(); })
                .ifThrowsThenGetDefaultOrElseMap(
                        obj -> 1,
                        0,
                        ArithmeticException.class);
        Assert.assertEquals(0, result);
    }

    @Test
    public void getOne() {
        int result = Try.evaluate(42, (Integer i) -> {})
                .ifThrowsThenGetDefaultOrElseMap(
                        obj -> 1,
                        0,
                        ArithmeticException.class);
        Assert.assertEquals(1, result);
    }

}
