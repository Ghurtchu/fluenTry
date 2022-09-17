package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public class PureCatchableMappableForCallables {

    @Test
    public void getDefault() {
        Integer result = Try.evaluate(() -> 42 / 0)
                .ifThrowsThenGetDefaultOrElseMap(
                        Function.identity(),
                        0,
                        ArithmeticException.class);
        Assert.assertEquals(result, Integer.valueOf(0));
    }

    @Test
    public void getSelf() {
        Integer result = Try.evaluate(() -> 42 / 1)
                .ifThrowsThenGetDefaultOrElseMap(
                        Function.identity(),
                        0,
                        ArithmeticException.class);
        Assert.assertEquals(result, Integer.valueOf(42));
    }

    @Test
    public void mapSuccess() {
        Integer result = Try.evaluate(() -> 42 / 6)
                .ifThrowsThenGetDefaultOrElseMap(
                        num -> num * 2,
                        0,
                        ArithmeticException.class);
        Assert.assertEquals(result, Integer.valueOf(14));
    }

    @Test
    public void registerParentExceptionOfArithmeticException() {
        Integer result = Try.evaluate(() -> 42 / 0)
                .ifThrowsThenGetDefaultOrElseMap(
                        Function.identity(),
                        0,
                        Exception.class);
        Assert.assertEquals(result, Integer.valueOf(0));
    }

    @Test
    public void registerParentExceptionOfArithmeticException2() {
        Integer result = Try.evaluate(() -> 42 / 0)
                .ifThrowsThenGetDefaultOrElseMap(
                        Function.identity(),
                        0,
                        RuntimeException.class);
        Assert.assertEquals(result, Integer.valueOf(0));
    }


}

