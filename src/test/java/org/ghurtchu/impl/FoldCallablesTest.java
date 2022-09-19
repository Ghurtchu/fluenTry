package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Random;

public class FoldCallablesTest {

    private final static Random RANDOM = new Random();

    @Test
    public void failAndHealWithZero() {
        int result = Try.of(() -> 42 / 0).fold(num -> num * 10, 0);
        Assert.assertEquals(0, result);
    }

    @Test
    public void succeedWithIntSelf() {
        int result = Try.of(() -> 100 / 5).fold(Function.identity(), 0);
        Assert.assertEquals(20, result);
    }

    @Test
    public void succeedWithTransformedInt() {
        int result = Try.of(() -> 100 / 5).fold(num -> num + 1, 0);
        Assert.assertEquals(21, result);
    }

    @Test
    public void failAndHealWithString() {
        String result = Try.of(() -> "Stay hydrated!".substring(0, 100000)).fold(str -> str.concat(str), "String");
        Assert.assertEquals("String", result);
    }

    @Test
    public void succeedWithStringSelf() {
        String result = Try.of(() -> "Stay hydrated!".substring(0, 4)).fold(Function.identity(), "String");
        Assert.assertEquals("Stay", result);
    }

    @Test
    public void succeedWithTransformedString() {
        String result = Try.of(() -> "Stay hydrated!".substring(0, 4)).fold(String::toUpperCase, "String");
        Assert.assertEquals("STAY", result);
    }

    @Test
    public void failAndHealWithEmptyList() {
        List<Integer> result = Try.of(() -> List.of(1, 2, 3, 4, 5).subList(1, 10000)).fold(Function.identity(), Collections.emptyList());
        Assert.assertEquals(new ArrayList<>(), result);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void succeedWithSelfList() {
        List<Integer> result = Try.of(() -> List.of(1, 2, 3, 4, 5)).fold(Function.identity(), Collections.emptyList());
        Assert.assertEquals(List.of(1, 2, 3, 4, 5), result);
    }

    @Test
    public void succeedWithTransformedList() {
        List<Integer> result = Try.of(() -> List.of(1, 2, 3, 4, 5))
                .fold(list -> list.stream().map(i -> i * 2).collect(Collectors.toList()), Collections.emptyList());
        Assert.assertEquals(List.of(2, 4, 6, 8, 10), result);
    }

}