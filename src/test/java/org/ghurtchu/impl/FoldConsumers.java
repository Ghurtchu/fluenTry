package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public class FoldConsumers {

    @Test
    public void failAndHealWithZero() {
        int result = Try.evaluate(42, (Integer i) -> {
            System.out.println("got " + i);
            throw new RuntimeException("something bad happened");
        }).fold(obj -> 1, 0);
        Assert.assertEquals(0, result);
    }

    @Test
    public void succeedWithEmptyObject() {
        Object success = Try.evaluate(100, (Integer i) -> {
            System.out.println("got " + i);
        }).fold(Function.identity(), 0);
        Assert.assertTrue(success != null);
    }

    @Test
    public void failAndHealWithString() {
        String result = Try.evaluate("Stay hydrated!", (String s) -> {
            System.out.println("got " + s);
            throw new RuntimeException("something bad happened");
        }).fold(obj -> "???", "String");
        Assert.assertEquals("String", result);
    }

}
