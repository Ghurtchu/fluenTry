package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public class PureCatchableMappableForFunctionsTest {

    @Test
    public void getDefault() {
        int result = Try.evaluate(42, i -> i / 0)
                .ifThrowsThenGetDefaultOrElseMap(
                        Function.identity(),
                        0,
                        ArithmeticException.class);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void getSelf() {
        int result = Try.evaluate(42, i -> i / 0)
                .ifThrowsThenGetDefaultOrElseMap(
                        Function.identity(),
                        0,
                        ArithmeticException.class);
        Assert.assertEquals(0, result);
    }

    @Test
    public void mapSuccess() {
        int result = Try.evaluate(42, i -> i / 6)
                .ifThrowsThenGetDefaultOrElseMap(
                        num -> num * 2,
                        0,
                        ArithmeticException.class);
        Assert.assertEquals(14, result);
    }

    @Test
    public void registerParentExceptionOfArithmeticException() {
        int result = Try.evaluate(42, i -> i / 0)
                .ifThrowsThenGetDefaultOrElseMap(
                        Function.identity(),
                        0,
                        Exception.class);
        Assert.assertEquals(0, result);
    }

    @Test
    public void registerParentExceptionOfArithmeticException2() {
        int result = Try.evaluate(42, i -> i / 0)
                .ifThrowsThenGetDefaultOrElseMap(
                        Function.identity(),
                        0,
                        RuntimeException.class);
        Assert.assertEquals(0, result);
    }

}
