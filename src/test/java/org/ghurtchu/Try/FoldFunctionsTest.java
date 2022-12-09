package org.ghurtchu.Try;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public class FoldFunctionsTest {

    @Test
    public void failAndHealWithZero() {
        int result = Try.of(5, i -> i / 0).fold(Function.identity(), 0);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void succeedWithIntSelf() {
        int result = Try.of(5, i -> i + 1).fold(Function.identity(), 0);
        Assert.assertEquals(result, 6);
    }

    @Test
    public void succeedWithTransformedInt() {
        int result = Try.of(5, i -> i + 1).fold(i -> i * 2, 0);
        Assert.assertEquals(result, 12);
    }

    @Test
    public void failAndHealWithString() {
        String result = Try.of("str", (String s) -> s.substring(0, 10000)).fold(Function.identity(), "Default String");
        Assert.assertEquals(result, "Default String");
    }

    @Test
    public void succeedWithStringSelf() {
        String result = Try.of("str", (String s) -> s.substring(0, 1)).fold(Function.identity(), "Default String");
        Assert.assertEquals(result, "s");
    }

    @Test
    public void succeedWithTransformedString() {
        String result = Try.of("str", (String s) -> s.substring(0, 1)).fold(s -> s.concat(s), "Default String");
        Assert.assertEquals(result, "ss");
    }

}
